package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GetAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetAnswerService answerService;

    @Test
    void getAnswer() throws Exception {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);

        doReturn(answer)
                .when(answerService)
                .get(answer.getResourceId());

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/answers/{resourceId}", answer.getResourceId())
        );

        // then
        result.andExpect(status().isOk());
    }


}