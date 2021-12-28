package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
public class GetQuizRequest {

    @NotNull
    private final QuizCategory category;

    @Range(min = QuizConstants.MIN_PAGE_SIZE, max = QuizConstants.MAX_PAGE_SIZE)
    private final int size;

    @Range(min = QuizConstants.MIN_PAGE_NUMBER)
    private final int page;

}