package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AddQuizRequest {

    @NotBlank
    private final String title;

    @NotNull
    private final QuizCategory quizCategory;

    @NotEmpty
    private final List<QuizLevel> quizLevel;

}
