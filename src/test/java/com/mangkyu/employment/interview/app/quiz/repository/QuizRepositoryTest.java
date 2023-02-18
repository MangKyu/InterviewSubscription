package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.JpaTestConfig;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.service.QuizSearchCondition;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@JpaTestConfig
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @ParameterizedTest
    @MethodSource("validQuizParameters")
    public void search_QueryOnly_QuizValid(final String query) {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        initForPaging(quizCategory);

        final QuizSearchCondition searchCondition = QuizSearchCondition.builder()
                .query(query)
                .categories(null)
                .levels(null)
                .build();

        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.search(searchCondition, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // when

        // then
        assertThat(result).isEqualTo(8);
    }

    @ParameterizedTest
    @MethodSource("invalidQuizParameters")
    public void search_QueryOnly_QuizInvalid(final String query) {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        initForPaging(quizCategory);

        final QuizSearchCondition searchCondition = QuizSearchCondition.builder()
                .query(query)
                .categories(null)
                .levels(null)
                .build();

        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.search(searchCondition, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // when

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void search_LevelsOnly() {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        initForPaging(quizCategory);

        final QuizSearchCondition searchCondition = QuizSearchCondition.builder()
                .query(null)
                .categories(Collections.emptySet())
                .levels(new HashSet<>(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR)))
                .build();

        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.search(searchCondition, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // when

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void search_CategoriesOnly() {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        initForPaging(quizCategory);

        final QuizSearchCondition searchCondition = QuizSearchCondition.builder()
                .query(null)
                .categories(new HashSet<>(Arrays.asList(QuizCategory.ALGORITHM, QuizCategory.DATABASE)))
                .levels(Collections.emptySet())
                .build();

        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.search(searchCondition, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // when

        // then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void search_AllEmpty() {
        // given
        final QuizCategory quizCategory = QuizCategory.JAVA;
        initForPaging(quizCategory);

        final QuizSearchCondition searchCondition = QuizSearchCondition.builder()
                .query(null)
                .categories(Collections.emptySet())
                .levels(Collections.emptySet())
                .build();

        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.search(searchCondition, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // when

        // then
        assertThat(result).isEqualTo(8);
    }

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
        initForPaging(quizCategory);

        // when
        boolean hasNext = true;
        int page = 0;
        int size = 3;
        long result = 0;
        while (hasNext) {
            final PageRequest pageRequest = PageRequest.of(page++, size);
            final Page<Quiz> pageQuiz = quizRepository.findByQuizCategoryIsAndIsEnableTrue(quizCategory, pageRequest);

            result += pageQuiz.getContent().size();
            hasNext = pageQuiz.hasNext();
        }

        // then
        assertThat(result).isEqualTo(quizRepository.countByQuizCategoryAndIsEnableTrue(quizCategory));
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
        final Long result1 = quizRepository.countByQuizCategoryAndIsEnableTrue(QuizCategory.JAVA);
        final Long result2 = quizRepository.countByQuizCategoryAndIsEnableTrue(QuizCategory.DATABASE);

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
        final List<Quiz> unsolvedQuizList1 = quizRepository.customFindByIdNotInAndQuizLevelAndIsEnableTrue(new HashSet<>(Arrays.asList(savedQuiz1.getId(), savedQuiz2.getId())), QuizLevel.NEW);
        final List<Quiz> unsolvedQuizList2 = quizRepository.customFindByIdNotInAndQuizLevelAndIsEnableTrue(Collections.emptySet(), QuizLevel.NEW);

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
        final List<Quiz> unsolvedQuizList = quizRepository.findByQuizLevelAndIsEnableTrue(QuizLevel.NEW);

        // then
        assertThat(unsolvedQuizList.size()).isEqualTo(3);
    }

    private void initForPaging(final QuizCategory quizCategory) {
        final Quiz savedQuiz1 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz2 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz3 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz4 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz5 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz6 = quizRepository.save(EntityCreationUtils.quiz("quiz", quizCategory, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)));
        final Quiz savedQuiz7 = quizRepository.save(EntityCreationUtils.quiz("quiz", QuizCategory.ALGORITHM, Collections.singletonList(QuizLevel.SENIOR)));
        final Quiz savedQuiz8 = quizRepository.save(EntityCreationUtils.quiz("quiz", QuizCategory.DATABASE, Collections.singletonList(QuizLevel.SENIOR)));

        savedQuiz1.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz1)));
        savedQuiz2.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz2)));
        savedQuiz3.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz3)));
        savedQuiz4.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz4)));
        savedQuiz5.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz5)));
        savedQuiz6.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz6)));
        savedQuiz7.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz7)));
        savedQuiz8.setAnswer(answerRepository.save(EntityCreationUtils.answer(savedQuiz8)));
    }

    private static Stream<Arguments> validQuizParameters() {
        return Stream.of(
                Arguments.of("q"),
                Arguments.of("qui"),
                Arguments.of("ui"),
                Arguments.of("iz")
        );
    }

    private static Stream<Arguments> invalidQuizParameters() {
        return Stream.of(
                Arguments.of("banana"),
                Arguments.of("taxi"),
                Arguments.of("mangkyu"),
                Arguments.of("gosudeath")
        );
    }

}