package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetQuizRequest {

    @NotNull
    private QuizCategory category;

    @Builder.Default
    @Range(min = QuizConstants.MIN_PAGE_SIZE, max = QuizConstants.MAX_PAGE_SIZE)
    private int size = QuizConstants.DEFAULT_PAGE_SIZE;

    @Builder.Default
    @Range(min = QuizConstants.MIN_PAGE_NUMBER)
    private int page = QuizConstants.DEFAULT_PAGE_NUMBER;

}