package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.MailService;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
import com.mangkyu.employment.interview.app.member.service.GetMemberService;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendQuizCronJob {

    private final GetMemberService memberService;
    private final QuizService quizService;
    private final MailService mailService;
    private final SolvedQuizService solvedQuizService;

    /**
     * https://crontab.guru/#0_01_*_*_1,4
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronExpression.html
     */

//    @Scheduled(cron = "*/30 * * * * *") // every 30 seconds
    @Scheduled(cron = "0 15 0 * * ?")
    @Transactional
    public void sendQuizMail() {
        final DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        final List<Member> memberList = memberService.getEnabledUserList(QuizDay.findQuizDay(dayOfWeek));
        log.info("========== sendQuizMail member={} ============", memberList.size());
        for (final Member member : memberList) {
            sendUnsolvedQuizForUser(member);
        }
    }

    private void sendUnsolvedQuizForUser(final Member member) {
        final List<Quiz> unsolvedQuizList = quizService.getUnsolvedQuizList(member.getId(), member.getQuizLevel(), member.getQuizCategorySet());
        final boolean isLastMail = isLastMail(unsolvedQuizList, member.getQuizSize());

        final List<Quiz> randomQuizList = quizService.getRandomQuizListUnderLimit(unsolvedQuizList, member.getQuizSize());
        if (isLastMail) {
            member.disableUser();
        }

        mailService.sendMail(member.getEmail(), randomQuizList, isLastMail);
        solvedQuizService.addSolvedQuizList(member, randomQuizList);
    }

    private boolean isLastMail(final List<Quiz> quizList, final Integer quizSize) {
        return quizList.size() <= quizSize;
    }

}
