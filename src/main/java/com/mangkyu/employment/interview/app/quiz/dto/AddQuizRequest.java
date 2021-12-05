package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class AddQuizRequest {

    private final String title;
    private final QuizCategory quizCategory;
    private final List<QuizLevel> quizLevel;

}
