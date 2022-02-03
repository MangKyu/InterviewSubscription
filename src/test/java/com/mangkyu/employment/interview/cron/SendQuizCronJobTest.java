package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.MailService;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
import com.mangkyu.employment.interview.app.member.service.MemberService;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendQuizCronJobTest {

    @InjectMocks
    private SendQuizCronJob target;

    @Mock
    private MemberService memberService;
    @Mock
    private QuizService quizService;
    @Mock
    private MailService mailService;
    @Mock
    private SolvedQuizService solvedQuizService;

    private Member member;
    private List<Member> memberList;
    private QuizDay quizDay;

    @BeforeEach
    public void init() {
        member = Member.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizSize(3)
                .build();

        memberList = Collections.singletonList(member);
        quizDay = QuizDay.findQuizDay(LocalDate.now().getDayOfWeek());
    }

    @Test
    public void sendQuizMailDaily_UserNotExists() {
        // given
        doReturn(Collections.emptyList())
                .when(memberService)
                .getEnabledUserList(quizDay);

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(0)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        verify(quizService, times(0)).getRandomQuizListUnderLimit(anyList(), anyInt());
        verify(memberService, times(0)).disableUser(member);
        verify(mailService, times(0)).sendMail(anyString(), anyList(), anyBoolean());
        verify(solvedQuizService, times(0)).addSolvedQuizList(any(Member.class), anyList());
    }

    @Test
    public void sendQuizMailDaily_NotLastMail() {
        // given
        final List<Quiz> unsolvedQuizList = quizList(5);
        final List<Quiz> randomQuizList = unsolvedQuizList.subList(0, 3);

        doReturn(memberList)
                .when(memberService)
                .getEnabledUserList(quizDay);
        doReturn(unsolvedQuizList)
                .when(quizService)
                .getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        doReturn(randomQuizList)
                .when(quizService)
                .getRandomQuizListUnderLimit(unsolvedQuizList, member.getQuizSize());

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        verify(quizService, times(1)).getRandomQuizListUnderLimit(unsolvedQuizList, member.getQuizSize());
        verify(memberService, times(0)).disableUser(member);
        verify(mailService, times(1)).sendMail(member.getEmail(), randomQuizList, false);
        verify(solvedQuizService, times(1)).addSolvedQuizList(member, randomQuizList);
    }

    @Test
    public void sendQuizMailDaily_LastMail() {
        // given
        final List<Quiz> unsolvedQuizList = quizList(3);
        final List<Quiz> randomQuizList = unsolvedQuizList.subList(0, 3);

        doReturn(memberList)
                .when(memberService)
                .getEnabledUserList(quizDay);
        doReturn(unsolvedQuizList)
                .when(quizService)
                .getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        doReturn(randomQuizList)
                .when(quizService)
                .getRandomQuizListUnderLimit(unsolvedQuizList, member.getQuizSize());

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        verify(quizService, times(1)).getRandomQuizListUnderLimit(unsolvedQuizList, member.getQuizSize());
        verify(memberService, times(1)).disableUser(member);
        verify(mailService, times(1)).sendMail(member.getEmail(), randomQuizList, true);
        verify(solvedQuizService, times(1)).addSolvedQuizList(member, randomQuizList);
    }

    private List<Quiz> quizList(final int size) {
        final List<Quiz> quizList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            quizList.add(quiz());
        }

        return quizList;
    }

    private Quiz quiz() {
        return Quiz.builder()
                .build();
    }

}