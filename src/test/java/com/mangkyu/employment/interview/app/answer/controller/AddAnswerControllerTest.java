package com.mangkyu.employment.interview.app.answer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AddAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("provideParameters")
    void addAnswerFail_InvalidParameter(final String quizResourceId, final String desc) throws Exception {
        // given
        final AddAnswerRequest request = AddAnswerRequest.builder()
                .quizResourceId(quizResourceId)
                .description(desc)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/answers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void addAnswerSuccess() throws Exception {
        // given
        final AddAnswerRequest request = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();


        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/answers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isNoContent());
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(null, desc(1)),
                Arguments.of(UUID.randomUUID().toString(), null),
                Arguments.of(UUID.randomUUID().toString(), desc(1000))
        );
    }

    private static String desc(final int count) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        return stringBuilder.toString();
    }

}