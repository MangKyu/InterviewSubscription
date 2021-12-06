package com.mangkyu.employment.interview.app.solvedquiz.repository;

import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolvedQuizRepository extends JpaRepository <SolvedQuizEntity, Long> {

    List<SolvedQuizEntity> findAllByUser_Id(Long userId);

}
