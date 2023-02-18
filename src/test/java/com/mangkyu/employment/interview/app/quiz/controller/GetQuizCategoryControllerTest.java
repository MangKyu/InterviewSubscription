package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponse;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GetQuizCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizService quizService;

    @Test
    void getCategory() throws Exception {
        // given
        final QuizCategoryResponse quizCategoryResponse = QuizCategoryResponse.builder()
                .count(15L)
                .code(QuizCategory.JAVA.name())
                .title(QuizCategory.JAVA.getTitle())
                .desc(QuizCategory.JAVA.getDesc())
                .build();

        doReturn(List.of(quizCategoryResponse))
                .when(quizService)
                .getQuizCategoryList();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/quizzes/categories")
        );

        // then
        result.andExpect(status().isOk());
    }

}