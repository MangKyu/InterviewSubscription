package com.mangkyu.employment.interview.enums.common;

import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumMapperKey {

    QUIZ_LEVEL(QuizLevel.class),
    QUIZ_CATEGORY(QuizCategory.class),
    ;

    private final Class<? extends EnumMapperType> enumClass;

}
