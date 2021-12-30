package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.app.common.pagination.ResponseMetaData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetQuizResponseHolder extends ResponseMetaData {

    private final List<GetQuizResponse> quizList;

    @Builder
    public GetQuizResponseHolder(final List<GetQuizResponse> quizList, final boolean hasNext, final int page, final int size, final long totalElements) {
        super(hasNext, page, size, totalElements);
        this.quizList = quizList;
    }
}