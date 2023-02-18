package com.mangkyu.employment.interview.app.quiz.entity;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QuizzesTest {


    @Test
    public void getRandomQuizListUnderLimitSuccess_UnderLimit() {
        // given
        final Quizzes quizzes = new Quizzes(List.of(quiz(4L)));

        // when
        final List<Quiz> result = quizzes.getRandomQuizListUnderLimit(3);

        // then
        assertThat(result.size()).isEqualTo(quizzes.size());
    }

    @Test
    public void getRandomQuizListUnderLimitSuccess_OverLimit() {
        // given
        final Quizzes quizzes = quizzes();

        // when
        final List<Quiz> result = quizzes.getRandomQuizListUnderLimit(3);

        // then
        assertThat(result.size()).isEqualTo(3);
    }


    @Test
    void 마지막메일_True(){
        // given
        Quizzes quizzes = quizzes();

        // when
        boolean result = quizzes.isLastMail(5);

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

    private Quiz quiz(final long id) {
        final Quiz quiz = Quiz.builder()
                .title("quiz")
                .quizLevel(List.of(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(List.of(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        ReflectionTestUtils.setField(quiz, "id", id);
        ReflectionTestUtils.setField(quiz, "resourceId", UUID.randomUUID().toString());
        ReflectionTestUtils.setField(quiz, "createdAt", LocalDateTime.now());
        return quiz;
    }

    private Quizzes quizzes() {
        final List<Quiz> unsolvedQuizList = new ArrayList<>();

        unsolvedQuizList.add(quiz(1L));
        unsolvedQuizList.add(quiz(2L));
        unsolvedQuizList.add(quiz(3L));
        unsolvedQuizList.add(quiz(4L));

        return new Quizzes(unsolvedQuizList);
    }

}