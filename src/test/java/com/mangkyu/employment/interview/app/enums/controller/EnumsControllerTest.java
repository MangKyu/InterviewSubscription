package com.mangkyu.employment.interview.app.enums.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.config.enums.EnumMapperConfig;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EnumsControllerTest {

    @InjectMocks
    private EnumsController target;

    @Spy
    private EnumMapperFactory enumMapperFactory = new EnumMapperConfig().enumMapperFactory();

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void getEnumsQuizCategoryList() throws Exception {
        // given
        final String url = "/enums/quiz-categories";

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
    public void getEnumsQuizLevelList() throws Exception {
        // given
        final String url = "/enums/quiz-levels";

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
    public void getEnumsQuizDayList() throws Exception {
        // given
        final String url = "/enums/quiz-days";

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