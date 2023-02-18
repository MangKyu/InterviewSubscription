package com.mangkyu.employment.interview.app.answer.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAnswerRequest {

    @NotBlank
    private String quizResourceId;

    private String resourceId = String.valueOf(UUID.randomUUID());

    @NotBlank
    @Size(max = 5000)
    private String description;

}
