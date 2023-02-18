package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateMemberServiceTest {

    @InjectMocks
    private UpdateMemberService target;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void disableUserSuccess() {
        // given
        final Member member = user();

        // when
        target.disableUser(member);

        // then

        // verify
        verify(memberRepository, times(1)).save(member);
    }

    private Member user() {
        final Member member = Member.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();
        member.setIsEnable(true);
        return member;
    }

}