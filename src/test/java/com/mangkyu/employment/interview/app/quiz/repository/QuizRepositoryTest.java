package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void selectQuizCountByCategory() {
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
                .quizCategory(QuizCategory.DATABASE)
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
        final Long result1 = quizRepository.countByQuizCategory(QuizCategory.JAVA);
        final Long result2 = quizRepository.countByQuizCategory(QuizCategory.DATABASE);

        // then
        assertThat(result1).isEqualTo(3);
        assertThat(result2).isEqualTo(1);
    }

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
    public void selectQuizNotInAndLevelAndQuizCategory() {
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
                .quizCategory(QuizCategory.DATABASE)
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

        final Set<Long> idSet = new HashSet<>(Arrays.asList(savedQuiz1.getId(), savedQuiz2.getId(), savedQuiz3.getId(), savedQuiz4.getId()));

        // when
        final List<Quiz> unsolvedQuizList1 = quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(Collections.emptySet(), new HashSet<>(Arrays.asList(QuizCategory.JAVA, QuizCategory.ALGORITHM, QuizCategory.CULTURE)), QuizLevel.NEW);
        final List<Quiz> unsolvedQuizList2 = quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(Collections.emptySet(), new HashSet<>(Arrays.asList(QuizCategory.CULTURE, QuizCategory.NETWORK)), QuizLevel.NEW);
        final List<Quiz> unsolvedQuizList3 = quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(Collections.emptySet(), new HashSet<>(Arrays.asList(QuizCategory.JAVA, QuizCategory.ALGORITHM, QuizCategory.DATABASE)), QuizLevel.NEW);
        final List<Quiz> unsolvedQuizList4 = quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(idSet, new HashSet<>(Arrays.asList(QuizCategory.JAVA, QuizCategory.DATABASE)), QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList1.size()).isEqualTo(2);
        assertThat(unsolvedQuizList2.size()).isEqualTo(0);
        assertThat(unsolvedQuizList3.size()).isEqualTo(3);
        assertThat(unsolvedQuizList4.size()).isEqualTo(0);
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

        // when
        final List<Quiz> unsolvedQuizList1 = quizRepository.customFindByIdNotInAndQuizLevel(new HashSet<>(Arrays.asList(savedQuiz1.getId(), savedQuiz2.getId())), QuizLevel.NEW);
        final List<Quiz> unsolvedQuizList2 = quizRepository.customFindByIdNotInAndQuizLevel(Collections.emptySet(), QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList1.size()).isEqualTo(1);
        assertThat(unsolvedQuizList2.size()).isEqualTo(3);
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