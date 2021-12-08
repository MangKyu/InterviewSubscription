package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void insertQuiz() {
        // given
        final Quiz quiz = Quiz.builder()
                .title("quiz")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        // when
        final Quiz result = quizRepository.save(quiz);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuizLevel().size()).isEqualTo(quiz.getQuizLevel().size());
    }

    @Test
    public void selectQuizNotInAndLevel() {
        // given
        final Quiz quiz1 = Quiz.builder()
                .title("quiz1")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz2 = Quiz.builder()
                .title("quiz2")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz3 = Quiz.builder()
                .title("quiz3")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz4 = Quiz.builder()
                .title("quiz4")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        final Quiz savedQuiz1 = quizRepository.save(quiz1);
        final Quiz savedQuiz2 = quizRepository.save(quiz2);
        final Quiz savedQuiz3 = quizRepository.save(quiz3);
        final Quiz savedQuiz4 = quizRepository.save(quiz4);

        System.out.println(savedQuiz1.getId());
        System.out.println(savedQuiz2.getId());
        System.out.println(savedQuiz3.getId());
        System.out.println(savedQuiz4.getId());

        // when
        final List<Quiz> unsolvedQuizList = quizRepository.findByIdNotInAndQuizLevel(new HashSet<>(Arrays.asList(savedQuiz1.getId(), savedQuiz2.getId())), QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList.size()).isEqualTo(1);
    }

    @Test
    public void selectQuizLevel() {
        // given
        final Quiz quiz1 = Quiz.builder()
                .title("quiz1")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz2 = Quiz.builder()
                .title("quiz2")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz3 = Quiz.builder()
                .title("quiz3")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final Quiz quiz4 = Quiz.builder()
                .title("quiz4")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        quizRepository.save(quiz1);
        quizRepository.save(quiz2);
        quizRepository.save(quiz3);
        quizRepository.save(quiz4);

        // when
        final List<Quiz> unsolvedQuizList = quizRepository.findByQuizLevel(QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList.size()).isEqualTo(3);
    }

}