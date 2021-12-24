package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum QuizDay implements EnumMapperType {

    MONDAY("Monday", DayOfWeek.MONDAY),
    TUESDAY("Tuesday", DayOfWeek.TUESDAY),
    WEDNESDAY("Wednesday", DayOfWeek.WEDNESDAY),
    THURSDAY("Thursday", DayOfWeek.THURSDAY),
    FRIDAY("Friday", DayOfWeek.FRIDAY),
    SATURDAY("Saturday", DayOfWeek.SATURDAY),
    SUNDAY("Sunday", DayOfWeek.SUNDAY),
    ;

    private final String desc;
    private final DayOfWeek dayOfWeek;

    @Override
    public String getCode() {
        return name();
    }

    public static QuizDay findQuizDay(final DayOfWeek dayOfWeek) {
        return Arrays.stream(QuizDay.values())
                .filter(v -> v.dayOfWeek == dayOfWeek)
                .findAny().orElse(QuizDay.SUNDAY);
    }

}
