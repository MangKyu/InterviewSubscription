package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetQuizResponse {

    private final long id;
    private final String title;
    private final QuizCategory quizCategory;
    private final List<String> quizLevelList;
    private final long createdAt;

}