package com.mangkyu.employment.interview.app.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class QuizCategoryResponse {

    private final long count;
    private final String code;
    private final String title;
    private final String desc;

}
