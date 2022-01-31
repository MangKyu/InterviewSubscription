package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizLevel implements EnumMapperType {

    NEW("New", "신입", true),
    JUNIOR("Junior", "주니어", true),
    SENIOR("Senior", "시니어", true),
    ;

    private final String title;
    private final String desc;
    private final boolean expose;

}
