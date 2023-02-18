package com.mangkyu.employment.interview.app.answer.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAnswerResponse {

    private String resourceId;
    private String quizResourceId;
    private String description;
    private long createdAt;

}