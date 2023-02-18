package com.mangkyu.employment.interview.app.quiz.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuizzesTest {

    @Test
    void 마지막메일_True(){
        // given
        Quizzes quizzes = quizzes();

        // when
        boolean result = quizzes.isLastMail(3);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막메일_False(){
        // given
        Quizzes quizzes = quizzes();

        // when
        boolean result = quizzes.isLastMail(1);

        // then
        assertThat(result).isFalse();
    }

    private Quizzes quizzes() {
        return new Quizzes(List.of(new Quiz(), new Quiz()));
    }

}