package com.mangkyu.employment.interview.cron;

import com.mangkyu.employment.interview.app.mail.service.MailService;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.service.SolvedQuizService;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendQuizCronJobTest {

    @InjectMocks
    private SendQuizCronJob target;

    @Mock
    private UserService userService;
    @Mock
    private QuizService quizService;
    @Mock
    private MailService mailService;
    @Mock
    private SolvedQuizService solvedQuizService;

    private User user;
    private List<User> userList;

    @BeforeEach
    public void init() {
        user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        userList = Collections.singletonList(user);
    }

    @Test
    public void sendQuizMailEveryWeekSuccess_UserNotExists() {
        // given
        doReturn(Collections.emptyList())
                .when(userService)
                .getEnabledUserList();

        // when
        target.sendQuizMailEveryWeek();

        // then

        // verify
        verify(quizService, times(0)).getUnsolvedQuizList(user.getId(), user.getQuizLevel());
        verify(quizService, times(0)).getRandomQuizListUnderLimit(anyList());
        verify(userService, times(0)).disableUser(user);
        verify(mailService, times(0)).sendMail(anyString(), anyList(), anyBoolean());
        verify(solvedQuizService, times(0)).addSolvedQuizList(any(User.class), anyList());
    }

    @Test
    public void sendQuizMailEveryWeekSuccess_NotLastMail() {
        // given
        final List<Quiz> unsolvedQuizList = quizList(5);
        final List<Quiz> randomQuizList = unsolvedQuizList.subList(0, 3);

        doReturn(userList)
                .when(userService)
                .getEnabledUserList();
        doReturn(unsolvedQuizList)
                .when(quizService)
                .getUnsolvedQuizList(user.getId(), user.getQuizLevel());
        doReturn(randomQuizList)
                .when(quizService)
                .getRandomQuizListUnderLimit(unsolvedQuizList);

        // when
        target.sendQuizMailEveryWeek();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(user.getId(), user.getQuizLevel());
        verify(quizService, times(1)).getRandomQuizListUnderLimit(unsolvedQuizList);
        verify(userService, times(0)).disableUser(user);
        verify(mailService, times(1)).sendMail(user.getEmail(), randomQuizList, false);
        verify(solvedQuizService, times(1)).addSolvedQuizList(user, randomQuizList);
    }

    @Test
    public void sendQuizMailEveryWeekSuccess_LastMail() {
        // given
        final List<Quiz> unsolvedQuizList = quizList(3);
        final List<Quiz> randomQuizList = unsolvedQuizList.subList(0, 3);

        doReturn(userList)
                .when(userService)
                .getEnabledUserList();
        doReturn(unsolvedQuizList)
                .when(quizService)
                .getUnsolvedQuizList(user.getId(), user.getQuizLevel());
        doReturn(randomQuizList)
                .when(quizService)
                .getRandomQuizListUnderLimit(unsolvedQuizList);

        // when
        target.sendQuizMailEveryWeek();

        // then

        // verify
        verify(quizService, times(1)).getUnsolvedQuizList(user.getId(), user.getQuizLevel());
        verify(quizService, times(1)).getRandomQuizListUnderLimit(unsolvedQuizList);
        verify(userService, times(1)).disableUser(user);
        verify(mailService, times(1)).sendMail(user.getEmail(), randomQuizList, true);
        verify(solvedQuizService, times(1)).addSolvedQuizList(user, randomQuizList);
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