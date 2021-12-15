package com.mangkyu.employment.interview.app.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AddQuizRequestHolder {

    private final List<AddQuizRequest> quizList;

}
