package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAnswerServiceTest {

    @InjectMocks
    private UpdateAnswerService answerService;

    @Mock
    private QuizService quizService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    public void addAnswer_Success_Modify() throws RestApiException {
        // given
        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);
        ReflectionTestUtils.setField(quiz, "answer", answer);

        doReturn(quiz)
                .when(quizService)
                .findQuiz(addAnswerRequest.getQuizResourceId());

        // when
        answerService.add(addAnswerRequest);

        // then

        // verify
        verify(answerRepository, times(0)).save(answer);
    }

    @Test
    public void addAnswer_Success_Insert() throws RestApiException {
        // given
        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();
        final Quiz quiz = EntityCreationUtils.quiz();

        doReturn(quiz)
                .when(quizService)
                .findQuiz(addAnswerRequest.getQuizResourceId());

        // when
        answerService.add(addAnswerRequest);

        // then
        verify(answerRepository, times(1)).save(any(Answer.class));
    }

}