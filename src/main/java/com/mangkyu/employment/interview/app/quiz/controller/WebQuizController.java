package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.service.AnswerService;
import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WebQuizController {

    private final QuizService quizService;
    private final AnswerService answerService;

    @GetMapping("/quiz/editView/{resourceId}")
    public String addUserPage(@PathVariable final String resourceId, final Model model) {
        final GetQuizResponse quiz = quizService.getQuiz(resourceId);
        model.addAttribute("quiz", quiz);

        if (StringUtils.isNotBlank(quiz.getAnswerResourceId())) {
            final GetAnswerResponse answer = answerService.getAnswer(quiz.getAnswerResourceId());
            model.addAttribute("answer", answer);
        } else {
            model.addAttribute("answer", GetAnswerResponse.builder().build());
        }

        return "quiz/editQuiz";
    }
}
