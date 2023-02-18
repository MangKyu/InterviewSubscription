package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.SendMailService;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.service.GetMemberService;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.entity.Quizzes;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
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
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendQuizCronJobTest {

    @InjectMocks
    private SendQuizCronJob target;

    @Mock
    private GetMemberService memberService;
    @Mock
    private GetQuizService quizService;
    @Mock
    private SendMailService mailService;
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

        memberList = List.of(member);
        quizDay = QuizDay.findQuizDay(LocalDate.now().getDayOfWeek());
    }

    @Test
    public void sendQuizMailDaily_UserNotExists() {
        // given
        doReturn(List.of())
                .when(memberService)
                .getEnabledUserList(quizDay);

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(0)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        verify(mailService, times(0)).sendMail(anyString(), anyList(), anyBoolean());
        verify(solvedQuizService, times(0)).addSolvedQuizList(any(Member.class), anyList());
    }

    @Test
    public void sendQuizMailDaily_NotLastMail() {
        // given
        Quizzes quizzes = new Quizzes(quizList(5));

        doReturn(memberList)
                .when(memberService)
                .getEnabledUserList(quizDay);
        doReturn(quizzes)
                .when(quizService)
                .getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
    }

    @Test
    public void sendQuizMailDaily_LastMail() {
        // given
        Quizzes quizzes = new Quizzes(quizList(3));

        doReturn(memberList)
                .when(memberService)
                .getEnabledUserList(quizDay);
        doReturn(quizzes)
                .when(quizService)
                .getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());

        // when
        target.sendQuizMail();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
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