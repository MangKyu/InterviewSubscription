package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.MailService;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.service.UserService;
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

    private final UserService userService;
    private final QuizService quizService;
    private final MailService mailService;
    private final SolvedQuizService solvedQuizService;

    /**
     * https://crontab.guru/#0_01_*_*_1,4
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronExpression.html
     */

//    @Scheduled(cron = "*/30 * * * * *") // every 30 seconds
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void sendQuizMail() {
        final DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        final List<User> userList = userService.getEnabledUserList(QuizDay.findQuizDay(dayOfWeek));
        for (final User user : userList) {
            System.out.println(userList.size());
            sendUnsolvedQuizForUser(user);
        }
    }

    private void sendUnsolvedQuizForUser(final User user) {
        final List<Quiz> unsolvedQuizList = quizService.getUnsolvedQuizList(user.getId(), user.getQuizLevel(), user.getQuizCategorySet());
        final boolean isLastMail = isLastMail(unsolvedQuizList, user.getQuizSize());

        final List<Quiz> randomQuizList = quizService.getRandomQuizListUnderLimit(unsolvedQuizList, user.getQuizSize());
        if (isLastMail) {
            userService.disableUser(user);
        }

        mailService.sendMail(user.getEmail(), randomQuizList, isLastMail);
        solvedQuizService.addSolvedQuizList(user, randomQuizList);
    }

    private boolean isLastMail(final List<Quiz> quizList, final Integer quizSize) {
        return quizList.size() <= quizSize;
    }

}
