package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizLevel implements EnumMapperType {

    NEW("New Developers", "신입"),
    JUNIOR("Junior Developers", "주니어"),
    SENIOR("Senior Developers", "시니어"),
    ;

    private final String title;
    private final String desc;

}
