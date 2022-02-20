package com.mangkyu.employment.interview.app.solvedquiz.converter;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SolvedQuizDtoConverter {

    public static SolvedQuiz convert(final Member member, final Quiz quiz) {
        return SolvedQuiz.builder()
                .member(member)
                .quiz(quiz).build();
    }

}