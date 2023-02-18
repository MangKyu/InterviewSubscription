package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.entity.PagingQuizzes;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.entity.Quizzes;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.mangkyu.employment.interview.app.quiz.constants.QuizConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GetQuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetQuizService quizService;

    @Autowired
    private EnumMapperFactory enumMapperFactory;

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(null, 10, 10),
                Arguments.of(QuizCategory.JAVA, MIN_PAGE_SIZE - 1, MIN_PAGE_NUMBER),
                Arguments.of(QuizCategory.JAVA, MAX_PAGE_SIZE + 1, MIN_PAGE_NUMBER),
                Arguments.of(QuizCategory.JAVA, MIN_PAGE_SIZE, MIN_PAGE_NUMBER - 1)
        );
    }

    @Test
    void getQuiz() throws Exception {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final String resourceId = quiz.getResourceId();

        doReturn(quiz)
                .when(quizService)
                .getQuiz(resourceId);

        final QuizCategory quizCategory = QuizCategory.JAVA;
        final EnumMapperValue enumMapperValue = EnumMapperValue.builder()
                .code(quizCategory.name())
                .title(quizCategory.getTitle())
                .desc(quizCategory.getDesc())
                .expose(true)
                .build();

        doReturn(Collections.singletonList(enumMapperValue))
                .when(enumMapperFactory)
                .get(EnumMapperKey.QUIZ_CATEGORY);

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes/{resourceId}", resourceId)
        );

        // then
        result.andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void getQuizListFail_BadRequest(final QuizCategory quizCategory, final int size, final int page) throws Exception {
        // given
        final String category = quizCategory == null
                ? null
                : quizCategory.name();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes")
                        .param("category", category)
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void getQuizList() throws Exception {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();

        final Pageable pageable = PageRequest.of(MIN_PAGE_NUMBER, MIN_PAGE_SIZE);
        final PageImpl<Quiz> quizPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        final PagingQuizzes quizzes = PagingQuizzes.builder()
                .quizzes(new Quizzes(List.of(quiz)))
                .hasNext(quizPage.hasNext())
                .pageNumber(quizPage.nextOrLastPageable().getPageNumber())
                .pageSize(quizPage.nextOrLastPageable().getPageSize())
                .totalPages(quizPage.getTotalPages())
                .build();

        doReturn(quizzes)
                .when(quizService)
                .getQuizList(any(GetQuizRequest.class));

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes")
                        .param("category", QuizCategory.JAVA.name())
                        .param("size", String.valueOf(MIN_PAGE_SIZE))
                        .param("page", String.valueOf(MIN_PAGE_NUMBER))
        );

        // then
        result.andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void searchQuizFail_InvalidParameter(final QuizCategory quizCategory, final int size, final int page) throws Exception {
        // given
        if (quizCategory == null) {
            return;
        }

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes/search")
                        .param("size", String.valueOf(size))
                        .param("page", String.valueOf(page))
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void searchQuizSuccess() throws Exception {
        // given
        final GetQuizResponse quizResponse = GetQuizResponse.builder()
                .title("quiz")
                .quizLevelList(Arrays.asList(QuizLevel.JUNIOR.name(), QuizLevel.SENIOR.name()))
                .category(enumMapperValue(QuizCategory.JAVA).getTitle())
                .build();

        final Pageable pageable = PageRequest.of(MIN_PAGE_NUMBER, MIN_PAGE_SIZE);
        final PageImpl<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        final GetQuizResponseHolder getQuizResponseHolder = GetQuizResponseHolder.builder()
                .quizList(Collections.singletonList(quizResponse))
                .hasNext(quizPage.hasNext())
                .page(quizPage.nextOrLastPageable().getPageNumber())
                .size(quizPage.nextOrLastPageable().getPageSize())
                .totalPages(quizPage.getTotalPages())
                .build();

        doReturn(getQuizResponseHolder)
                .when(quizService)
                .searchQuizList(any(SearchQuizListRequest.class));

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes/search")
                        .param("size", String.valueOf(MIN_PAGE_SIZE))
                        .param("page", String.valueOf(MIN_PAGE_NUMBER))
        );

        // then
        result.andExpect(status().isOk());
    }

    private EnumMapperValue enumMapperValue(final EnumMapperType enumMapperType) {
        return EnumMapperValue.builder()
                .code(enumMapperType.name())
                .title(enumMapperType.getTitle())
                .desc(enumMapperType.getDesc())
                .build();
    }

}