package com.mangkyu.employment.interview.enums.common;

import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumMapperKey {

    QUIZ_LEVEL(QuizLevel.class),
    QUIZ_CATEGORY(QuizCategory.class),
    QUIZ_DAY(QuizDay.class),
    ;

    private final Class<? extends EnumMapperType> enumClass;

}
