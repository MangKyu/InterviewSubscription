package com.mangkyu.employment.interview.app.member.repository;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository <Member, Long> {

    List<Member> findAllByIsEnableTrueAndQuizDaySetIs(final QuizDay QuizDay);

}
