package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.AnswerService;
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
    private AnswerService answerService;

    @Test
    public void getAnswer() throws Exception {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final String url = "/answers/" + resourceId;
        final GetAnswerResponse getAnswerResponse = GetAnswerResponse.builder()
                .resourceId(resourceId)
                .quizResourceId(UUID.randomUUID().toString())
                .description("설명")
                .createdAt(System.currentTimeMillis())
                .build();

        doReturn(getAnswerResponse).when(answerService).getAnswer(resourceId);

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("resourceId").value(getAnswerResponse.getResourceId()))
                .andExpect(jsonPath("quizResourceId").value(getAnswerResponse.getQuizResourceId()))
                .andExpect(jsonPath("description").value(getAnswerResponse.getDescription()));
    }


}