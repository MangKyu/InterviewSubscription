package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum QuizDay implements EnumMapperType {

    MONDAY("Monday", "월요일", DayOfWeek.MONDAY, true),
    TUESDAY("Tuesday", "화요일", DayOfWeek.TUESDAY, true),
    WEDNESDAY("Wednesday", "수요일", DayOfWeek.WEDNESDAY, true),
    THURSDAY("Thursday", "목요일", DayOfWeek.THURSDAY, true),
    FRIDAY("Friday", "금요일", DayOfWeek.FRIDAY, true),
    SATURDAY("Saturday", "토요일", DayOfWeek.SATURDAY, true),
    SUNDAY("Sunday", "일요일", DayOfWeek.SUNDAY, true),
    ;

    private final String title;
    private final String desc;
    private final DayOfWeek dayOfWeek;private final boolean expose;

    public static QuizDay findQuizDay(final DayOfWeek dayOfWeek) {
        return Arrays.stream(QuizDay.values())
                .filter(v -> v.dayOfWeek == dayOfWeek)
                .findAny().orElse(QuizDay.SUNDAY);
    }

}
