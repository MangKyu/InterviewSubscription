package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAnswerServiceTest {

    @InjectMocks
    private DeleteAnswerService answerService;
    @Mock
    private GetAnswerService getAnswerService;
    @Mock
    private AnswerRepository answerRepository;

    @Test
    void deleteAnswer_Success() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);

        doReturn(answer)
                .when(getAnswerService)
                .get(answer.getResourceId());

        // when
        answerService.delete(answer.getResourceId());

        // then

        // verify
        verify(answerRepository, times(1)).delete(answer);
    }

}