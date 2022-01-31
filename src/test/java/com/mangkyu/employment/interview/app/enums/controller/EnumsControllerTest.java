package com.mangkyu.employment.interview.app.enums.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.config.enums.EnumMapperConfig;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnumsController.class)
@Import(EnumMapperConfig.class)
class EnumsControllerTest {

    @SpyBean
    private EnumMapperFactory enumMapperFactory;

    @Autowired
    private MockMvc mockMvc;

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
        assertThat(new Gson().fromJson(stringResponse, List.class).size()).isEqualTo(Arrays.stream(QuizCategory.values()).filter(QuizCategory::isExpose).count());
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