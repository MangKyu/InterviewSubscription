package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository <QuizEntity, Long> {

    List<QuizEntity> findByIdNotIn(Set<Long> quizIdList);

}
