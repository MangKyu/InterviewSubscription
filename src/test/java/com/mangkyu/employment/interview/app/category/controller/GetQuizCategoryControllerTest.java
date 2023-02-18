package com.mangkyu.employment.interview.app.category.controller;

import com.mangkyu.employment.interview.app.category.service.GetQuizCategoryService;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
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
    private GetQuizCategoryService quizService;

    @Test
    void getCategory() throws Exception {
        // given
        final EnumMapperValue enumMapperValue = EnumMapperValue.builder()
                .code(QuizCategory.JAVA.name())
                .title(QuizCategory.JAVA.getTitle())
                .desc(QuizCategory.JAVA.getDesc())
                .expose(false)
                .build();

        doReturn(List.of(enumMapperValue))
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