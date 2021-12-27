package com.mangkyu.employment.interview.app.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class QuizCategoryResponseHolder {

    private final List<QuizCategoryResponse> categoryList;

}