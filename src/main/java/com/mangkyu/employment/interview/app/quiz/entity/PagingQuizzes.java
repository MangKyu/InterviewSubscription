/*
 *
 *  PagingQuizzes.java 2023-02-18
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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingQuizzes {

    private Quizzes quizzes;
    private boolean hasNext;
    private int pageNumber;
    private int pageSize;
    private int totalPages;

}
