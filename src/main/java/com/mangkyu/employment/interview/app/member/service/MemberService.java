package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.dto.AddMemberRequest;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void addUser(final AddMemberRequest addMemberRequest) {
        final Member member = modelMapper.map(addMemberRequest, Member.class);
        memberRepository.save(member);
    }

    @Transactional
    public void disableUser(final Member member) {
        member.setIsEnable(false);
        memberRepository.save(member);
    }

    public List<Member> getEnabledUserList(final QuizDay QuizDay) {
        return memberRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay);
    }
}
