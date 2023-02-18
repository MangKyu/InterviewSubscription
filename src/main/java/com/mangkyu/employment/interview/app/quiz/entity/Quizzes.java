/*
 *
 *  Quizzies.java 2023-02-18
 *
 *  Copyright 2023 WorksMobile Corp. All rights Reserved.
 *  WorksMobile PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package com.mangkyu.employment.interview.app.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quizzes {

    private List<Quiz> quizList;

    public boolean isLastMail(final Integer quizSize) {
        return quizList.size() <= quizSize;
    }


    public List<Quiz> getRandomQuizListUnderLimit(final Integer quizSize) {
        return quizList.size() <= quizSize
                ? quizList
                : createRandomQuizListUnderLimit(quizList, quizSize);
    }

    private List<Quiz> createRandomQuizListUnderLimit(final List<Quiz> quizList, final Integer quizSize) {
        final Random rand = new Random();
        final List<Quiz> randomQuizList = new ArrayList<>();

        for (int i = 0; i < quizSize; i++) {
            final int randomIndex = rand.nextInt(quizList.size());
            final Quiz quiz = quizList.get(randomIndex);
            randomQuizList.add(quiz);
            quizList.remove(quiz);
        }

        return randomQuizList;
    }

    public int size() {
        return quizList.size();
    }
}
