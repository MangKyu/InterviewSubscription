package com.mangkyu.employment.interview.app.answer.repository;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO: Divide by ReadOnlyRepository and WriteOnlyRepository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByResourceId(final String resourceId);

}