package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizSearchCondition {

    private String query;
    private Set<QuizCategory> categories;
    private Set<QuizLevel> levels;

}