package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetAnswerServiceTest {

    @InjectMocks
    private GetAnswerService answerService;

    @Mock
    private GetQuizService quizService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    void getAnswer_Fail_NotExists() {
        // given
        final String resourceId = UUID.randomUUID().toString();

        doReturn(Optional.empty())
                .when(answerRepository)
                .findByResourceId(resourceId);

        // when
        final RestApiException result = assertThrows(
                RestApiException.class,
                () -> answerService.get(resourceId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    void getAnswer_Success() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);

        doReturn(Optional.of(answer))
                .when(answerRepository)
                .findByResourceId(answer.getResourceId());

        // when
        final Answer result = answerService.get(answer.getResourceId());

        // then
        assertThat(result.getDescription()).isEqualTo(answer.getDescription());
    }

}