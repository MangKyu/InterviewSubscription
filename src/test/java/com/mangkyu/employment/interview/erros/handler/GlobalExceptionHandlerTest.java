package com.mangkyu.employment.interview.erros.handler;

import com.google.gson.Gson;
//import com.mangkyu.employment.interview.app.quiz.controller.GetQuizController;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.errorcode.ErrorCode;
import com.mangkyu.employment.interview.erros.response.ErrorResponse;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
//
//    @InjectMocks
//    private GetQuizController target;
//
//    @Mock
//    private QuizService quizService;
//
//    private MockMvc mockMvc;
//    @Spy
//    private Validator validator = new LocalValidatorFactoryBean();
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(target)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build();
//    }
//
//    @Test
//    public void handleQuizException() throws Exception {
//        // given
//        final String resourceId = UUID.randomUUID().toString();
//        final String url = "/quizzes/" + resourceId;
//        final ErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;
//
//        doThrow(new RestApiException(errorCode)).when(quizService).getQuiz(resourceId);
//
//        // when
//        final ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.get(url)
//        );
//
//        // then
//        final ResultActions resultActions = result.andExpect(status().isNotFound());
//        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
//        final ErrorResponse errorResponse = new Gson().fromJson(stringResponse, ErrorResponse.class);
//
//
//        assertThat(errorResponse.getCode()).isEqualTo(errorCode.name());
//        assertThat(errorResponse.getMessage()).isEqualTo(errorCode.getMessage());
//    }
//
//    @Test
//    public void handleIllegalArgument() throws Exception {
//        // given
//        final String resourceId = UUID.randomUUID().toString();
//        final String url = "/quizzes/" + resourceId;
//        final String message = "message";
//
//        doThrow(new IllegalArgumentException(message)).when(quizService).getQuiz(resourceId);
//
//        // when
//        final ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.get(url)
//        );
//
//        // then
//        final ResultActions resultActions = result.andExpect(status().isBadRequest());
//        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
//        final ErrorResponse errorResponse = new Gson().fromJson(stringResponse, ErrorResponse.class);
//
//
//        assertThat(errorResponse.getCode()).isEqualTo(CommonErrorCode.INVALID_PARAMETER.name());
//        assertThat(errorResponse.getMessage()).isEqualTo(message);
//    }
//
//    @Test
//    public void handleMethodArgumentNotValid() throws Exception {
//        // given
//        final String url = "/quiz";
//        final ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
//
//        final AddQuizRequest addQuizRequest = AddQuizRequest.builder()
//                .title("Title")
//                .quizLevel(Collections.singletonList(QuizLevel.NEW))
//                .build();
//
//        // when
//        final ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.post(url)
//                        .content(new Gson().toJson(addQuizRequest))
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // then
//        final ResultActions resultActions = result.andExpect(status().isBadRequest());
//        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
//        final ErrorResponse errorResponse = new Gson().fromJson(stringResponse, ErrorResponse.class);
//
//        assertThat(errorResponse.getCode()).isEqualTo(errorCode.name());
//        assertThat(errorResponse.getMessage()).isEqualTo(errorCode.getMessage());
//        assertThat(errorResponse.getErrors().size()).isEqualTo(1);
//    }

}