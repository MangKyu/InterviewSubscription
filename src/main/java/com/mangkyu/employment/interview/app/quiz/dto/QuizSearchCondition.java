package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class QuizSearchCondition {

    private final String query;
    private final Set<QuizCategory> categories;
    private final Set<QuizLevel> levels;

}