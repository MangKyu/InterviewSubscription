package com.mangkyu.employment.interview.app.answer.entity;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void answer초기화(){
        // given
        Quiz quiz = EntityCreationUtils.quiz();
        quiz.setAnswer(new Answer());

        Answer answer = Answer.builder()
                .quiz(quiz)
                .build();

        // when
        answer.clearAnswer();

        // then
        assertThat(answer.getQuiz().getAnswer()).isNull();
    }

}