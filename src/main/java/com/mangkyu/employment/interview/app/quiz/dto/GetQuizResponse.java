package com.mangkyu.employment.interview.app.quiz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetQuizResponse {

    private final long id;
    private final String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final EnumMapperValue category;

    private final List<String> quizLevelList;
    private final long createdAt;

}