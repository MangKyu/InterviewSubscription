package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuizListRequest {

    private String query;

    private Set<QuizCategory> categories;

    private Set<QuizLevel> levels;

    @Builder.Default
    @Range(min = QuizConstants.MIN_PAGE_NUMBER)
    private int page = QuizConstants.DEFAULT_PAGE_NUMBER;

    @Builder.Default
    @Range(min = QuizConstants.MIN_PAGE_SIZE, max = QuizConstants.MAX_PAGE_SIZE)
    private int size = QuizConstants.DEFAULT_PAGE_SIZE;

}