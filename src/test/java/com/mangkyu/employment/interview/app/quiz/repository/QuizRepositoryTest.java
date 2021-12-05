package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void insertQuiz() {
        // given
        final QuizEntity quiz = QuizEntity.builder()
                .title("quiz")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        // when
        final QuizEntity result = quizRepository.save(quiz);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuizLevel().size()).isEqualTo(quiz.getQuizLevel().size());
    }

}