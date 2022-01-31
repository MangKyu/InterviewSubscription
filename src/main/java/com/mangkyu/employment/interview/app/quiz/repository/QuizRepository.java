package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {

    Optional<Quiz> findByResourceId(final String resourceId);

    Page<Quiz> findByQuizCategoryIsAndIsEnableTrue(final QuizCategory quizCategory, final Pageable pageable);

    Long countByQuizCategoryAndIsEnableTrue(final QuizCategory category);

    default List<Quiz> customFindByIdNotInAndQuizLevelAndIsEnableTrue(final Set<Long> quizIdSet, final QuizLevel quizLevel) {
        return quizIdSet.isEmpty()
                ? findByQuizLevelAndIsEnableTrue(quizLevel)
                : findByIdNotInAndQuizLevelAndIsEnableTrue(quizIdSet, quizLevel);
    }

    List<Quiz> findByQuizLevelAndIsEnableTrue(final QuizLevel quizLevel);

    List<Quiz> findByIdNotInAndQuizLevelAndIsEnableTrue(final Set<Long> quizIdSet, final QuizLevel quizLevel);

    /**
    * 여기 아래는 메일 발송에 사용되는 API들이므로 IsEnable 처리를 제외함
    *
    *
    **/

    default List<Quiz> customFindByIdNotInAndQuizCategoryInAndQuizLevel(final Set<Long> quizIdSet, final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel) {
        return quizIdSet.isEmpty()
                ? findByQuizCategoryInAndQuizLevel(quizCategorySet, quizLevel)
                : findByIdNotInAndQuizCategoryInAndQuizLevel(quizIdSet, quizCategorySet, quizLevel);
    }

    List<Quiz> findByQuizCategoryInAndQuizLevel(final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel);

    List<Quiz> findByIdNotInAndQuizCategoryInAndQuizLevel(final Set<Long> quizIdSet, final Set<QuizCategory> quizCategorySet, final QuizLevel quizLevel);

}