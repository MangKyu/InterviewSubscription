package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository <QuizEntity, Long> {

    List<QuizEntity> findByIdNotInAndQuizLevel(final Set<Long> quizIdList, final QuizLevel quizLevel);

    List<QuizEntity> findByQuizLevel(final QuizLevel quizLevel);

}
