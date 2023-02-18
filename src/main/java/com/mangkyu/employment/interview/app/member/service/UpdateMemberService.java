package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService {

    private final MemberRepository memberRepository;

    public void disableUser(final Member member) {
        member.setIsEnable(false);
        memberRepository.save(member);
    }

}
