package com.mangkyu.employment.interview.app.quiz.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuizConstants {

    public static final int DEFAULT_QUIZ_SIZE = 3;
    public static final int MINIMUM_QUIZ_SIZE = 1;
    public static final int MAXIMUM_QUIZ_SIZE = 5;

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int MIN_PAGE_NUMBER = 0;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int DEFAULT_PAGE_SIZE = 6;
    public static final int MAX_PAGE_SIZE = 30;
}
