package com.mangkyu.employment.interview.app.common.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Builder
@RequiredArgsConstructor
@ToString
public class CursorPageable<T> {

    private final T response;
    private final ResponseMetaData responseMetaData;

    private CursorPageable(final T response, final Page<?> page) {
        this.response = response;
        this.responseMetaData = new ResponseMetaData(
                page.hasNext(),
                page.nextOrLastPageable().getPageNumber(),
                page.nextOrLastPageable().getPageSize(),
                page.getTotalElements());
    }

    public static <T> CursorPageable<T> of(final T response, final Page<?> page) {
        return new CursorPageable<>(response, page);
    }

}