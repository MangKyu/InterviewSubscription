package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
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

    @Test
    public void selectQuizNotInAndLevel() {
        // given
        final QuizEntity quiz1 = QuizEntity.builder()
                .title("quiz1")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz2 = QuizEntity.builder()
                .title("quiz2")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz3 = QuizEntity.builder()
                .title("quiz3")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz4 = QuizEntity.builder()
                .title("quiz4")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        final QuizEntity savedQuiz1 = quizRepository.save(quiz1);
        final QuizEntity savedQuiz2 = quizRepository.save(quiz2);
        final QuizEntity savedQuiz3 = quizRepository.save(quiz3);
        final QuizEntity savedQuiz4 = quizRepository.save(quiz4);

        System.out.println(savedQuiz1.getId());
        System.out.println(savedQuiz2.getId());
        System.out.println(savedQuiz3.getId());
        System.out.println(savedQuiz4.getId());

        // when
        final List<QuizEntity> unsolvedQuizList = quizRepository.findByIdNotInAndQuizLevel(new HashSet<>(Arrays.asList(savedQuiz1.getId(), savedQuiz2.getId())), QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList.size()).isEqualTo(1);
    }

    @Test
    public void selectQuizLevel() {
        // given
        final QuizEntity quiz1 = QuizEntity.builder()
                .title("quiz1")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz2 = QuizEntity.builder()
                .title("quiz2")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz3 = QuizEntity.builder()
                .title("quiz3")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        final QuizEntity quiz4 = QuizEntity.builder()
                .title("quiz4")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        quizRepository.save(quiz1);
        quizRepository.save(quiz2);
        quizRepository.save(quiz3);
        quizRepository.save(quiz4);

        // when
        final List<QuizEntity> unsolvedQuizList = quizRepository.findByQuizLevel(QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList.size()).isEqualTo(3);
    }

}