package com.mangkyu.employment.interview.app.quiz.converter;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.controller.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.controller.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuizDtoConverter {

    public static GetAnswerResponse convert(final Answer answer) {
        return GetAnswerResponse.builder()
                .resourceId(answer.getResourceId())
                .quizResourceId(answer.getQuiz().getResourceId())
                .description(answer.getDescription())
                .createdAt(Timestamp.valueOf(answer.getCreatedAt()).getTime())
                .build();
    }

    public static GetQuizResponse convert(final Quiz quiz) {
        return GetQuizResponse.builder()
                .resourceId(quiz.getResourceId())
                .answerResourceId(getAnswerResourceId(quiz))
                .title(quiz.getTitle())
                .category(quiz.getQuizCategory().getTitle())
                .quizLevelList(convert(quiz.getQuizLevel()))
                .createdAt(Timestamp.valueOf(quiz.getCreatedAt()).getTime())
                .build();
    }

    @Deprecated
    public static GetQuizResponse convert(final Quiz quiz, final EnumMapperValue enumMapperValue) {
        return GetQuizResponse.builder()
                .resourceId(quiz.getResourceId())
                .answerResourceId(getAnswerResourceId(quiz))
                .title(quiz.getTitle())
                .category(quiz.getQuizCategory().getTitle())
                .quizLevelList(convert(quiz.getQuizLevel()))
                .createdAt(Timestamp.valueOf(quiz.getCreatedAt()).getTime())
                .build();
    }

    private static List<String> convert(final List<QuizLevel> quizLevelList) {
        return quizLevelList.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static Answer convert(final AddAnswerRequest addAnswerRequest, final Quiz quiz) {
        return Answer.builder()
                .resourceId(addAnswerRequest.getResourceId())
                .quiz(quiz)
                .description(addAnswerRequest.getDescription()).build();
    }

    private static String getAnswerResourceId(final Quiz quiz) {
        return (quiz.getAnswer() == null)
                ? null
                : quiz.getAnswer().getResourceId();
    }

}