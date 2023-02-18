package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetMemberServiceTest {

    @InjectMocks
    private GetMemberService target;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void getEnabledUserList_Success() {
        // given
        final List<Member> enabledMemberList = List.of(user(), user());
        doReturn(enabledMemberList)
                .when(memberRepository)
                .findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.MONDAY);

        // when
        final List<Member> result = target.getEnabledUserList(QuizDay.MONDAY);

        // then
        assertThat(result.size()).isEqualTo(enabledMemberList.size());

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