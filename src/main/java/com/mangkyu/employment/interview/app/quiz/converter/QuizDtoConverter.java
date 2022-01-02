package com.mangkyu.employment.interview.app.quiz.converter;

import com.mangkyu.employment.interview.app.quiz.dto.GetQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class QuizDtoConverter {

    public static GetQuizResponse convert(final Quiz quiz) {
        return GetQuizResponse.builder()
                .resourceId(quiz.getResourceId())
                .title(quiz.getTitle())
                .quizLevelList(convert(quiz.getQuizLevel()))
                .createdAt(Timestamp.valueOf(quiz.getCreatedAt()).getTime())
                .build();
    }

    public static GetQuizResponse convert(final Quiz quiz, final EnumMapperValue enumMapperValue) {
        return GetQuizResponse.builder()
                .resourceId(quiz.getResourceId())
                .title(quiz.getTitle())
                .category(enumMapperValue)
                .quizLevelList(convert(quiz.getQuizLevel()))
                .createdAt(Timestamp.valueOf(quiz.getCreatedAt()).getTime())
                .build();
    }

    private static List<String> convert(final List<QuizLevel> quizLevelList) {
        return quizLevelList.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static GetQuizRequest convert(final int size, final int page, final QuizCategory category) {
        return GetQuizRequest.builder()
                .size(size)
                .page(page)
                .category(category)
                .build();
    }

}