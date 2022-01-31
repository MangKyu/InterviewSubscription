package com.mangkyu.employment.interview.app.user.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.user.dto.AddUserRequest;
import com.mangkyu.employment.interview.app.user.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addUserFail_EmailIsEmpty() throws Exception {
        // given
        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addUserFail_NotEmailFormat() throws Exception {
        // given
        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("asdas")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addUserFail_QuizLevelIsNull() throws Exception {
        // given
        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(null)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isBadRequest());

    }

    @Test
    public void addUserFail_QuizDaySetIsEmpty() throws Exception {
        // given
        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.NEW)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
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

        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
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

        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated());
    }

}