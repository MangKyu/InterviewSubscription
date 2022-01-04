package com.mangkyu.employment.interview.app.answer.dto;

import com.mangkyu.employment.interview.app.answer.constants.AnswerConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AddAnswerRequest {

    @NotBlank
    private final String quizResourceId;

    private final String resourceId = String.valueOf(UUID.randomUUID());

    @NotBlank
    @Size(max = AnswerConstants.MAX_ANSWER_SIZE)
    private final String description;

}
