package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetQuizRequest {

    private final int size;
    private final int page;
    private final QuizCategory category;

}