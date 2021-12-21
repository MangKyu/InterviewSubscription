package com.mangkyu.employment.interview.app.user.dto;

import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.enums.UserQuizCycle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.mangkyu.employment.interview.app.quiz.constants.QuizConstants.*;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AddUserRequest {

    @Email
    @NotBlank
    private final String email;

    @NotNull
    private final QuizLevel quizLevel;

    @Range(min = MINIMUM_QUIZ_SIZE, max = MAXIMUM_QUIZ_SIZE)
    private final Integer quizSize = DEFAULT_QUIZ_SIZE;

    @NotNull
    private final UserQuizCycle userQuizCycle;
}
