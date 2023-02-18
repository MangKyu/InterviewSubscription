package com.mangkyu.employment.interview.app.quiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.QuizSearchCondition;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizRepositoryCustom {

    Page<Quiz> search(QuizSearchCondition condition, final Pageable pageable);

}