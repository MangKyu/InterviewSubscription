package com.mangkyu.employment.interview.app.quiz.dto;

import com.mangkyu.employment.interview.app.common.pagination.ResponseMetaData;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetQuizResponseHolder extends ResponseMetaData {

    private final List<GetQuizResponse> quizList;
    private final EnumMapperValue category;

    @Builder
    public GetQuizResponseHolder(final List<GetQuizResponse> quizList, final EnumMapperValue category, final boolean hasNext, final int page, final int size, final int totalPages) {
        super(hasNext, page, size, totalPages);
        this.category = category;
        this.quizList = quizList;
    }
}