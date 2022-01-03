package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.common.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.app.common.erros.exception.QuizException;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
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
class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private QuizService quizService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    public void addAnswerSuccess() throws QuizException {
        // given
        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .desc("desc")
                .build();
        final Quiz quiz = EntityCreationUtils.quiz();

        doReturn(quiz).when(quizService).findQuiz(addAnswerRequest.getQuizResourceId());

        // when
        answerService.addAnswer(addAnswerRequest);

        // then
    }

    @Test
    public void getAnswerByQuizResourceIdFail_AnswerNotExists() {
        // given
        final String resourceId = UUID.randomUUID().toString();
        doReturn(Optional.empty()).when(answerRepository).findByResourceId(resourceId);

        // when
        final QuizException result = assertThrows(QuizException.class, () -> answerService.getAnswer(resourceId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void getAnswerByQuizResourceIdSuccess() throws QuizException {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);
        doReturn(Optional.of(answer)).when(answerRepository).findByResourceId(answer.getResourceId());

        // when
        final GetAnswerResponse result = answerService.getAnswer(answer.getResourceId());

        // then
        assertThat(result.getDesc()).isEqualTo(answer.getDesc());
    }

}