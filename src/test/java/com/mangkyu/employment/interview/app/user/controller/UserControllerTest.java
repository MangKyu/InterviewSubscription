package com.mangkyu.employment.interview.app.user.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.dto.AddUserRequest;
import com.mangkyu.employment.interview.app.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController target;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

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
    public void addUserSuccess() throws Exception {
        // given
        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(new Gson().toJson(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated());

        // then

    }

}