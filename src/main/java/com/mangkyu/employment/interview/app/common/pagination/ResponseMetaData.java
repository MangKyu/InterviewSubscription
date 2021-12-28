package com.mangkyu.employment.interview.app.common.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ResponseMetaData {

    private final boolean hasNext;
    private final int page;
    private final int size;

}