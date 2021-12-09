package com.mangkyu.employment.interview.app.mail.service;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @InjectMocks
    private MailService target;

    @Mock
    private JavaMailSender javaMailSender;

    private final String userEmail = "whalsrb1226@naver.com";

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(target, "START_MAIL_FORMAT", "{currentDate}");
        ReflectionTestUtils.setField(target, "BODY_MAIL_FORMAT", "{quiz.title}, {quiz.category}, {quiz.level}");
        ReflectionTestUtils.setField(target, "END_MAIL_FORMAT", "end mail format");
    }

    @Test
    public void sendMail_EmptyQuizList() {
        // given

        // when
        target.sendMail(userEmail, Collections.emptyList(), true);

        // then
    }

    @Test
    public void sendMailSuccess_LastMail() {
        // given
        doReturn(new MimeMessage(Session.getInstance(new Properties())))
                .when(javaMailSender)
                .createMimeMessage();

        // when
        target.sendMail(userEmail, quizList(), true);

        // then
    }

    @Test
    public void sendMailSuccess_NotLastMail() {
        // given
        doReturn(new MimeMessage(Session.getInstance(new Properties())))
                .when(javaMailSender)
                .createMimeMessage();

        // when
        target.sendMail(userEmail, quizList(), false);

        // then
    }

    private List<Quiz> quizList() {
        return Arrays.asList(
                quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR)),
                quiz(QuizCategory.TDD, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)),
                quiz(QuizCategory.SPRING, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR, QuizLevel.NEW))
        );
    }

    private Quiz quiz(final QuizCategory quizCategory, final List<QuizLevel> quizLevelList) {
        return Quiz.builder()
                .quizCategory(quizCategory)
                .quizLevel(quizLevelList).build();
    }
}