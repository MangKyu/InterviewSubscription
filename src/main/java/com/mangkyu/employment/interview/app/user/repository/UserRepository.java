package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository <User, Long> {

    List<User> findAllByIsEnableTrueAndQuizDaySetIs(final QuizDay QuizDay);

}
