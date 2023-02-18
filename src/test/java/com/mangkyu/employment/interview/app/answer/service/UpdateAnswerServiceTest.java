package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
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
    private GetQuizService quizService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    void updateAnswer_Success() {
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
                .getQuiz(addAnswerRequest.getQuizResourceId());

        // when
        answerService.add(addAnswerRequest);

        // then

        // verify
        verify(answerRepository, times(0)).save(answer);
    }

}