package com.mangkyu.employment.interview.enums.common;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class EnumMapperValue implements EnumMapperType {

    private final String code;
    private final String title;
    private final String desc;
    private final boolean expose;

    @Override
    public String name() {
        return code;
    }

}
