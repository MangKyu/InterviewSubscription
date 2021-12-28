package com.mangkyu.employment.interview.app.quiz.converter;

import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QuizDtoConverterTest {

    @Test
    public void convertQuizToGetQuizResponse() {
        // given
        final long id = -1L;
        final Quiz quiz = quiz(id);

        // when
        final GetQuizResponse result = QuizDtoConverter.convert(quiz);

        // then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getQuizCategory()).isEqualTo(quiz.getQuizCategory());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
    }

    private Quiz quiz(final long id) {
        final Quiz quiz = Quiz.builder()
                .title("quiz")
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        ReflectionTestUtils.setField(quiz, "id", id);
        ReflectionTestUtils.setField(quiz, "createdAt", LocalDateTime.now());
        return quiz;
    }

}