package com.mangkyu.employment.interview.app.quiz.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponse;
import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponseHolder;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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

}