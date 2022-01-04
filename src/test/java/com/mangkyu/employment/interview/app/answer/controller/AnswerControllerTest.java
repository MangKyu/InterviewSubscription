package com.mangkyu.employment.interview.app.answer.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.service.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AnswerControllerTest {

    @InjectMocks
    private AnswerController target;

    @Mock
    private AnswerService answerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

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
        result.andExpect(status().isCreated());
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