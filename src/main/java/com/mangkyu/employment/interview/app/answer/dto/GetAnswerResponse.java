package com.mangkyu.employment.interview.app.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAnswerResponse {

    private final String resourceId;
    private final String quizResourceId;
    private final String desc;
    private final long createdAt;

}