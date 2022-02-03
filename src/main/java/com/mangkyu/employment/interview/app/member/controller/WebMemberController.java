package com.mangkyu.employment.interview.app.member.controller;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebMemberController {

    private final EnumMapperFactory factory;

    @GetMapping("/user/addView")
    public String addUserPage(final Model model) {
        model.addAttribute("quizLevelList", factory.get(EnumMapperKey.QUIZ_LEVEL));
        model.addAttribute("quizCategoryList", factory.get(EnumMapperKey.QUIZ_CATEGORY));
        model.addAttribute("quizDayList", factory.get(EnumMapperKey.QUIZ_DAY));
        model.addAttribute("minQuizSize", QuizConstants.MINIMUM_QUIZ_SIZE);
        model.addAttribute("maxQuizSize", QuizConstants.MAXIMUM_QUIZ_SIZE);
        return "user/addUser";
    }

}
