package com.mangkyu.employment.interview.app.user.dto;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

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

    @NotEmpty
    private final Set<QuizDay> quizDaySet;

    @NotEmpty
    private final Set<QuizCategory> quizCategorySet;

}
