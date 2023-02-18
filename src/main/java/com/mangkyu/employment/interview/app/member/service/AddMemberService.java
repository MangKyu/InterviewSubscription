package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.controller.AddMemberRequest;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddMemberService {

    private final MemberRepository memberRepository;


    @Deprecated     // TODO: remove modelmapper
    private final ModelMapper modelMapper;

    public void add(final AddMemberRequest addMemberRequest) {
        final Member member = modelMapper.map(addMemberRequest, Member.class);
        memberRepository.save(member);
    }

}
