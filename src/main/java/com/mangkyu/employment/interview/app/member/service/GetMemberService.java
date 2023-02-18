package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberService {

    private final MemberRepository memberRepository;

    public List<Member> getEnabledUserList(final QuizDay QuizDay) {
        return memberRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay);
    }
}
