package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Page<Quiz> findByQuizCategoryIs(final QuizCategory quizCategory, final Pageable pageable);

    Long countByQuizCategory(final QuizCategory category);

    default List<Quiz> customFindByIdNotInAndQuizLevel(final Set<Long> quizIdSet, final QuizLevel quizLevel) {
        return quizIdSet.isEmpty()
                ? findByQuizLevel(quizLevel)
                : findByIdNotInAndQuizLevel(quizIdSet, quizLevel);
    }

    List<Quiz> findByQuizLevel(final QuizLevel quizLevel);

    List<Quiz> findByIdNotInAndQuizLevel(final Set<Long> quizIdSet, final QuizLevel quizLevel);

    default List<Quiz> customFindByIdNotInAndQuizCategoryInAndQuizLevel(final Set<Long> quizIdSet, final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel) {
        return quizIdSet.isEmpty()
                ? findByQuizCategoryInAndQuizLevel(quizCategorySet, quizLevel)
                : findByIdNotInAndQuizCategoryInAndQuizLevel(quizIdSet, quizCategorySet, quizLevel);
    }

    List<Quiz> findByQuizCategoryInAndQuizLevel(final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel);

    List<Quiz> findByIdNotInAndQuizCategoryInAndQuizLevel(final Set<Long> quizIdSet, final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel);

}