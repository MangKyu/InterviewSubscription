package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddAnswerServiceTest {

    @InjectMocks
    private AddAnswerService answerService;

    @Mock
    private QuizService quizService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    void addAnswer_Success_Insert() {
        // given
        final AddAnswerRequest request = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();
        final Quiz quiz = EntityCreationUtils.quiz();

        doReturn(quiz)
                .when(quizService)
                .findQuiz(request.getQuizResourceId());

        // when
        answerService.add(request);

        // then
        verify(answerRepository, times(1)).save(any(Answer.class));
    }

}