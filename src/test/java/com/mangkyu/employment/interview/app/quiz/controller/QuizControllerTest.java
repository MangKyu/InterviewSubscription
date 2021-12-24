package com.mangkyu.employment.interview.app.quiz.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.config.enums.EnumMapperConfig;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @InjectMocks
    private QuizController target;

    @Mock
    private QuizService quizService;
    @Spy
    private EnumMapperFactory enumMapperFactory = new EnumMapperConfig().enumMapperFactory();

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
    public void getQuizCategoryListSuccess() throws Exception {
        // given
        final String url = "/quiz/category";

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(new Gson().fromJson(stringResponse, List.class).size()).isEqualTo(QuizCategory.values().length);
    }

    @Test
    public void getQuizLevelListSuccess() throws Exception {
        // given
        final String url = "/quiz/level";

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(new Gson().fromJson(stringResponse, List.class).size()).isEqualTo(QuizLevel.values().length);
    }

    @Test
    public void getQuizDaySuccess() throws Exception {
        // given
        final String url = "/quiz/day";

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(new Gson().fromJson(stringResponse, List.class).size()).isEqualTo(QuizDay.values().length);
    }

}