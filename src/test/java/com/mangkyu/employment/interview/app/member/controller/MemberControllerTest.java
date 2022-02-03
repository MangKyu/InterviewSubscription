package com.mangkyu.employment.interview.app.member.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.member.dto.AddMemberRequest;
import com.mangkyu.employment.interview.app.member.service.MemberService;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addUserFail_EmailIsEmpty() throws Exception {
        // given
        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addUserFail_NotEmailFormat() throws Exception {
        // given
        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("asdas")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addUserFail_QuizLevelIsNull() throws Exception {
        // given
        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(null)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());

    }

    @Test
    public void addUserFail_QuizDaySetIsEmpty() throws Exception {
        // given
        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.NEW)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());

    }

    @Test
    public void addUserFail_QuizCategorySetIsEmpty() throws Exception {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addUserSuccess() throws Exception {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated());
    }

}