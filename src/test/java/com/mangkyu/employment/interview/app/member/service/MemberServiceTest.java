package com.mangkyu.employment.interview.app.member.service;

import com.mangkyu.employment.interview.app.member.controller.AddMemberRequest;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.config.modelmapper.ModelMapperConfig;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService target;

    @Mock
    private MemberRepository memberRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

    private final QuizDay quizDay = QuizDay.MONDAY;

    @Test
    public void addUserSuccess() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.SATURDAY);

        final AddMemberRequest addMemberRequest = AddMemberRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .build();

        // when
        target.addUser(addMemberRequest);

        // then
    }

    @Test
    public void disableUserSuccess() {
        // given
        final Member member = user(true);

        // when
        target.disableUser(member);

        // then

        // verify
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void getEnabledUserListSuccess() {
        // given
        final List<Member> enabledMemberList = Arrays.asList(user(true), user(true));
        doReturn(enabledMemberList).when(memberRepository).findAllByIsEnableTrueAndQuizDaySetIs(quizDay);

        // when
        final List<Member> result = target.getEnabledUserList(quizDay);

        // then
        assertThat(result.size()).isEqualTo(enabledMemberList.size());

        // verify
        verify(memberRepository, times(1)).findAllByIsEnableTrueAndQuizDaySetIs(quizDay);
    }

    private Member user(final boolean isEnable) {
        final Member member = Member.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();
        member.setIsEnable(isEnable);
        return member;
    }


}