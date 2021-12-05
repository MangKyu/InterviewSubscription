package com.mangkyu.employment.interview.app.solvedquiz.repository;

import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedQuizRepository extends JpaRepository <SolvedQuizEntity, Long> {
}
