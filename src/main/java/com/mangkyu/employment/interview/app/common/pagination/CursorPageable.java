package com.mangkyu.employment.interview.app.common.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor
@ToString
public class CursorPageable<T> {

    private final T response;
    private final ResponseMetaData responseMetaData;

    private CursorPageable(final T response, final boolean hasNext, final int page, final int size) {
        this.response = response;
        this.responseMetaData = new ResponseMetaData(hasNext, page, size);
    }

    public static <T> CursorPageable<T> of(final T response, final boolean hasNext, final int page, final int size) {
        return new CursorPageable<>(response, hasNext, page, size);
    }

}