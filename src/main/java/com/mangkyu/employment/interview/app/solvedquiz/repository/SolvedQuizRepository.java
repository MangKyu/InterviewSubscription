package com.mangkyu.employment.interview.app.solvedquiz.repository;

import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolvedQuizRepository extends JpaRepository <SolvedQuiz, Long> {

    List<SolvedQuiz> findAllByMember_Id(Long userId);

}
