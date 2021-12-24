package com.mangkyu.employment.interview.enums.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuizDayTest {

    @Test
    public void findQuizDay() {
        // given
        final DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;

        // when
        final QuizDay result = QuizDay.findQuizDay(dayOfWeek);

        // then
        assertThat(result).isEqualTo(QuizDay.SATURDAY);
    }

}