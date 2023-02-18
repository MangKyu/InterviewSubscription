package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DeleteAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetAnswerService answerService;

    @Test
    public void deleteAnswerSuccess() throws Exception {
        // given
        final String url = "/answers/" + UUID.randomUUID();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
        );

        // then
        result.andExpect(status().isNoContent());
    }

}