package com.mangkyu.employment.interview.app.quiz.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.app.quiz.dto.*;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @InjectMocks
    private QuizController target;

    @Mock
    private QuizService quizService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void getQuiz() throws Exception {
        // given
        final long id = -1L;
        final String url = "/quiz/" + id;
        final GetQuizResponse quizResponse = GetQuizResponse.builder()
                .title("quiz")
                .quizLevelList(Arrays.asList(QuizLevel.JUNIOR.name(), QuizLevel.SENIOR.name()))
                .quizCategory(QuizCategory.JAVA)
                .build();
        doReturn(quizResponse).when(quizService).getQuiz(id);

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        final GetQuizResponse getQuizResult = new Gson().fromJson(stringResponse, GetQuizResponse.class);

        assertThat(getQuizResult.getId()).isEqualTo(quizResponse.getId());
        assertThat(getQuizResult.getTitle()).isEqualTo(quizResponse.getTitle());
        assertThat(getQuizResult.getQuizCategory()).isEqualTo(quizResponse.getQuizCategory());
        assertThat(getQuizResult.getQuizCategory()).isEqualTo(quizResponse.getQuizCategory());
        assertThat(getQuizResult.getCreatedAt()).isEqualTo(quizResponse.getCreatedAt());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void getQuizListFail_BadRequest(final QuizCategory quizCategory, final int size, final int page) throws Exception {
        // given
        final String url = "/quizzes";
        final String category = quizCategory == null
                ? null
                : quizCategory.name();

        final GetQuizResponse quizResponse = GetQuizResponse.builder()
                .title("quiz")
                .quizLevelList(Arrays.asList(QuizLevel.JUNIOR.name(), QuizLevel.SENIOR.name()))
                .quizCategory(QuizCategory.JAVA)
                .build();
        final GetQuizResponseHolder getQuizResponseHolder = GetQuizResponseHolder.builder()
                .quizList(Collections.singletonList(quizResponse))
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("category", category)
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void getQuizList() throws Exception {
        // given
        final int size = QuizConstants.MIN_PAGE_SIZE;
        final int page = QuizConstants.MIN_PAGE_NUMBER;
        final String url = "/quizzes";

        final GetQuizResponse quizResponse = GetQuizResponse.builder()
                .title("quiz")
                .quizLevelList(Arrays.asList(QuizLevel.JUNIOR.name(), QuizLevel.SENIOR.name()))
                .quizCategory(QuizCategory.JAVA)
                .build();

        final Pageable pageable = PageRequest.of(page, size);
        final PageImpl<Quiz> quizPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        final GetQuizResponseHolder getQuizResponseHolder = GetQuizResponseHolder.builder()
                .quizList(Collections.singletonList(quizResponse))
                .hasNext(quizPage.hasNext())
                .page(quizPage.nextOrLastPageable().getPageNumber())
                .size(quizPage.nextOrLastPageable().getPageSize())
                .totalElements(quizPage.getTotalElements())
                .build();

        doReturn(getQuizResponseHolder).when(quizService).getQuizList(any(GetQuizRequest.class));

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("category", QuizCategory.JAVA.name())
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
        );

        // then
        result.andExpect(status().isOk());
    }

    @Test
    public void addQuiz() throws Exception {
        // given
        final String url = "/quiz";

        final AddQuizRequest addQuizRequest = AddQuizRequest.builder()
                .quizCategory(QuizCategory.JAVA)
                .title("Title")
                .quizLevel(Collections.singletonList(QuizLevel.NEW))
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(new Gson().toJson(addQuizRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void getQuizCategory() throws Exception {
        // given
        final String url = "/quiz/categories";
        final long count = 15L;

        final QuizCategoryResponse quizCategoryResponse = QuizCategoryResponse.builder()
                .count(count)
                .code(QuizCategory.JAVA.name())
                .title(QuizCategory.JAVA.getTitle())
                .desc(QuizCategory.JAVA.getDesc())
                .build();

        doReturn(Collections.singletonList(quizCategoryResponse)).when(quizService).getQuizCategoryList();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        final QuizCategoryResponseHolder responseHolder = new Gson().fromJson(stringResponse, QuizCategoryResponseHolder.class);
        assertThat(responseHolder.getCategoryList().size()).isEqualTo(1);
        assertThat(responseHolder.getCategoryList().get(0).getCount()).isEqualTo(count);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(null, 10, 10),
                Arguments.of(QuizCategory.JAVA, QuizConstants.MIN_PAGE_SIZE - 1, QuizConstants.MIN_PAGE_NUMBER),
                Arguments.of(QuizCategory.JAVA, QuizConstants.MAX_PAGE_SIZE + 1, QuizConstants.MIN_PAGE_NUMBER),
                Arguments.of(QuizCategory.JAVA, QuizConstants.MIN_PAGE_SIZE, QuizConstants.MIN_PAGE_NUMBER - 1)
        );
    }

}