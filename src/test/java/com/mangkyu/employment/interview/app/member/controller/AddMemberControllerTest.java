package com.mangkyu.employment.interview.app.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AddMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MethodSource("invalidAddMemberRequest")
    @ParameterizedTest
    void addmember_Fail_invalidBody(String email, QuizLevel quizLevel) throws Exception {
        // given
        final AddMemberRequest request = AddMemberRequest.builder()
                .email(email)
                .quizLevel(quizLevel)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());

    }

    private static List<Arguments> invalidAddMemberRequest() {
        return List.of(
                Arguments.of("", QuizLevel.JUNIOR),
                Arguments.of("asdas", QuizLevel.JUNIOR),
                Arguments.of("whalsrb1226@gmail.com", null)
        );
    }

    @Test
    void addmember_Fail_QuizDaySetIsEmpty() throws Exception {
        // given
        final AddMemberRequest request = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.NEW)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());

    }

    @Test
    void addmember_Fail_QuizCategorySetIsEmpty() throws Exception {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final AddMemberRequest request = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void addmember_Success() throws Exception {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final AddMemberRequest request = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated());
    }
}