package com.mangkyu.employment.interview.app.answer.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void getAnswer() throws Exception {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final String url = "/answer/" + resourceId;
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
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        final GetAnswerResponse getAnswerResult = new Gson().fromJson(stringResponse, GetAnswerResponse.class);

        assertThat(getAnswerResult.getResourceId()).isEqualTo(getAnswerResponse.getResourceId());
        assertThat(getAnswerResult.getDescription()).isEqualTo(getAnswerResponse.getDescription());
        assertThat(getAnswerResult.getCreatedAt()).isEqualTo(getAnswerResponse.getCreatedAt());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void addAnswerFail_InvalidParameter(final String quizResourceId, final String desc) throws Exception {
        // given
        final String url = "/answer";

        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(quizResourceId)
                .description(desc)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(new Gson().toJson(addAnswerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addAnswerSuccess() throws Exception {
        // given
        final String url = "/answer";

        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();


        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(new Gson().toJson(addAnswerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void putAnswerFail_InvalidParameter(final String quizResourceId, final String desc) throws Exception {
        // given
        final String url = "/answer";

        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(quizResourceId)
                .description(desc)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .content(new Gson().toJson(addAnswerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void putAnswerSuccess() throws Exception {
        // given
        final String url = "/answer";

        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();


        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .content(new Gson().toJson(addAnswerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteAnswerSuccess() throws Exception {
        // given
        final String url = "/answer/" + UUID.randomUUID();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
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