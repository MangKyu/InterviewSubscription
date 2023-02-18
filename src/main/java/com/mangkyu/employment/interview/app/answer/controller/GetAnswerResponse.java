package com.mangkyu.employment.interview.app.answer.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAnswerResponse {

    private final String resourceId;
    private final String quizResourceId;
    private final String description;
    private final long createdAt;

}