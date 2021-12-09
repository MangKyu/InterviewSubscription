package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.MailService;
import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendQuizCronJob {

    private final UserService userService;
    private final QuizService quizService;
    private final MailService mailService;
    private final SolvedQuizService solvedQuizService;

//    @Scheduled(cron = "*/30 * * * * *") // every 30 seconds
    @Scheduled(cron = "0 0 1 * * SUN") // every Sunday at 1am
    @Transactional
    public void sendQuizMailEveryWeek() {
        final List<User> userList = userService.getEnabledUserList();
        for (final User user : userList) {
            final List<Quiz> unsolvedQuizList = quizService.getUnsolvedQuizList(user.getId(), user.getQuizLevel());
            final boolean isLastMail = isLastMail(unsolvedQuizList);

            final List<Quiz> randomQuizList = quizService.getRandomQuizListUnderLimit(unsolvedQuizList);
            if (isLastMail) {
                userService.disableUser(user);
            }

            mailService.sendMail(user.getEmail(), randomQuizList, isLastMail);
            solvedQuizService.addSolvedQuizList(user, randomQuizList);
        }
    }

    private boolean isLastMail(final List<Quiz> quizList) {
        return quizList.size() <= QuizConstants.MAXIMUM_QUIZ_SIZE;
    }

}
