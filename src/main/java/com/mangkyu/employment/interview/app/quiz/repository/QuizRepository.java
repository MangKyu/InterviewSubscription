package com.mangkyu.employment.interview.app.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;

public interface QuizRepository extends JpaRepository <QuizEntity, Long> {
}
