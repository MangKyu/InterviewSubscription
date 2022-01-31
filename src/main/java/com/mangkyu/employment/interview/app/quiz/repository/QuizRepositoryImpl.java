package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.dto.QuizSearchCondition;
import com.mangkyu.employment.interview.app.quiz.entity.QQuiz;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<Quiz> search(final QuizSearchCondition condition, final Pageable pageable) {
        final QQuiz qQuiz = QQuiz.quiz;

        final List<Quiz> quizList = query.selectFrom(qQuiz)
                .where(titleLike(qQuiz, condition.getQuery()))
                .where(quizCategoriesIn(qQuiz, condition.getCategories()))
                .where(quizLevelsIn(qQuiz, condition.getLevels()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(qQuiz.answer.isNotNull())
                .fetch();

        final long count = query.selectFrom(qQuiz)
                .where(titleLike(qQuiz, condition.getQuery()))
                .where(quizCategoriesIn(qQuiz, condition.getCategories()))
                .where(quizLevelsIn(qQuiz, condition.getLevels()))
                .where(qQuiz.answer.isNotNull())
                .stream().count();

        return new PageImpl<>(quizList, pageable, count);
    }

    private BooleanExpression quizCategoriesIn(final QQuiz qQuiz, final Set<QuizCategory> categories) {
        return categories == null || categories.isEmpty()
                ? null
                : qQuiz.quizCategory.eqAny(
                JPAExpressions.select(qQuiz.quizCategory)
                        .from(qQuiz)
                        .where(qQuiz.quizCategory.in(categories))
        );
    }

    private BooleanBuilder quizLevelsIn(final QQuiz qQuiz, final Set<QuizLevel> quizLevels) {
        return quizLevels == null || quizLevels.isEmpty()
                ? null
                : quizLevelsMultipleIn(qQuiz, quizLevels);
    }

    private BooleanBuilder quizLevelsMultipleIn(final QQuiz qQuiz, final Set<QuizLevel> quizLevels) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (final QuizLevel quizLevel : quizLevels) {
            booleanBuilder.or(qQuiz.quizLevel.contains(quizLevel));
        }

        return booleanBuilder;
    }

    private BooleanExpression titleLike(final QQuiz qQuiz, final String query) {
        return StringUtils.isBlank(query) ? null : qQuiz.title.contains(query);
    }

}