package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.answer.controller.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WebQuizController {

    private final GetQuizService quizService;
    private final EnumMapperFactory enumMapperFactory;
    private final GetAnswerService answerService;

    @GetMapping("/quizzes/editView/{resourceId}")
    public String addUserPage(@PathVariable final String resourceId, final Model model) {
        com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz = quizService.getQuiz(resourceId);
        final GetQuizResponse response = QuizDtoConverter.convert(quiz, enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, quiz.getQuizCategory()));
        model.addAttribute("quiz", response);

        if (StringUtils.isNotBlank(response.getAnswerResourceId())) {
            final Answer answer = answerService.get(response.getAnswerResourceId());
            model.addAttribute("answer", QuizDtoConverter.convert(answer));
        } else {
            model.addAttribute("answer", GetAnswerResponse.builder().build());
        }

        return "quiz/editQuiz";
    }
}
