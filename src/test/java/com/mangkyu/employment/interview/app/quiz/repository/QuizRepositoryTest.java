package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void selectByResourceId() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        quizRepository.save(quiz);

        // when
        final Optional<Quiz> result = quizRepository.findByResourceId(quiz.getResourceId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitle()).isEqualTo(quiz.getTitle());
    }

    @Test
    public void selectQuizListByCategoryWithPaging() {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", QuizCategory.ALGORITHM, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));
        EntityCreationUtils.quiz("quiz", QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));

        // when
        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.findByQuizCategoryIs(quizCategory, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // then
        assertThat(result).isEqualTo(quizRepository.countByQuizCategory(quizCategory));
    }

    @Test
    public void selectQuizCountByCategory() {
        // given
        final Quiz quiz1 = EntityCreationUtils.quiz("quiz1", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz2 = EntityCreationUtils.quiz("quiz2", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz3 = EntityCreationUtils.quiz("quiz3", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz4 = EntityCreationUtils.quiz("quiz4", QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));

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
        final Quiz quiz = EntityCreationUtils.quiz();

        // when
        final Quiz result = quizRepository.save(quiz);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuizLevel().size()).isEqualTo(quiz.getQuizLevel().size());
    }

    @Test
    public void selectQuizNotInAndLevelAndQuizCategory() {
        // given
        final Quiz quiz1 = EntityCreationUtils.quiz("quiz1", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz2 = EntityCreationUtils.quiz("quiz2", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz3 = EntityCreationUtils.quiz("quiz3", QuizCategory.DATABASE, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz4 = EntityCreationUtils.quiz("quiz4", QuizCategory.JAVA, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));

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
        final Quiz quiz1 = EntityCreationUtils.quiz("quiz1", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz2 = EntityCreationUtils.quiz("quiz2", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz3 = EntityCreationUtils.quiz("quiz3", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz4 = EntityCreationUtils.quiz("quiz4", QuizCategory.JAVA, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));

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
        final Quiz quiz1 = EntityCreationUtils.quiz("quiz1", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz2 = EntityCreationUtils.quiz("quiz2", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz3 = EntityCreationUtils.quiz("quiz3", QuizCategory.JAVA, Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR));
        final Quiz quiz4 = EntityCreationUtils.quiz("quiz4", QuizCategory.JAVA, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR));

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