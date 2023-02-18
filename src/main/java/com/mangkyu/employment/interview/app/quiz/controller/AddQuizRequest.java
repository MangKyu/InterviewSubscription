package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddQuizRequest {

    @NotBlank
    private String title;

    @NotNull
    private QuizCategory quizCategory;

    private String resourceId = String.valueOf(UUID.randomUUID());

    @NotEmpty
    private List<QuizLevel> quizLevel;

}
