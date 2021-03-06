package com.mangkyu.employment.interview.app.mail.service;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.utils.DateUtils;
import com.mangkyu.employment.interview.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private String START_MAIL_FORMAT;
    private String BODY_MAIL_FORMAT;
    private String END_MAIL_FORMAT;

    @PostConstruct
    public void init() throws IOException {
        START_MAIL_FORMAT = FileUtils.readFileText("classpath:templates/startMailFormat.html");
        BODY_MAIL_FORMAT = FileUtils.readFileText("classpath:templates/bodyMailFormat.html");
        END_MAIL_FORMAT = FileUtils.readFileText("classpath:templates/endMailFormat.html");
    }

    public void sendMail(final String userEmail, final List<Quiz> quizList, final boolean isLastMail) {
        if (quizList.isEmpty()) {
            return;
        }

        try {
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(userEmail);
            messageHelper.setSubject(createSubjectText(isLastMail));

            final String mailText = createMailText(quizList);
            messageHelper.setText(mailText, true);
            mailSender.send(message);
        } catch (final MessagingException e) {
            log.error("send mail fail: ", e);
        }

    }

    private String createSubjectText(final boolean isLastMail) {
        final String subjectText = DateUtils.getCurrentDate() + " ?????????????????? ???????????? ???????????????.";
        return isLastMail
                ? subjectText + " (????????? ???????????????.)"
                : subjectText;
    }

    private String createMailText(final List<Quiz> quizList) {
        return createStartMailText() +
                createBodyMailText(quizList) +
                END_MAIL_FORMAT;
    }

    private String createStartMailText() {
        return START_MAIL_FORMAT.replaceAll("\\$\\{currentDate}", DateUtils.getCurrentDate());
    }

    private String createBodyMailText(final List<Quiz> quizList) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Quiz quiz : quizList) {
            final String indexReplacedText = BODY_MAIL_FORMAT.replaceAll("\\{index}", String.valueOf(quizList.indexOf(quiz) + 1));
            final String titleReplacedText = indexReplacedText.replaceAll("\\$\\{quiz.title}", quiz.getTitle());
            final String categoryReplacedText = titleReplacedText.replaceAll("\\$\\{quiz.category}", quiz.getQuizCategory().name());
            final String finalText = categoryReplacedText.replaceAll("\\$\\{quiz.level}", quiz.getQuizLevel().toString());
            stringBuilder.append(finalText);
        }

        return stringBuilder.toString();
    }

}
