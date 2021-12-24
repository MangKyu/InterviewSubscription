package com.mangkyu.employment.interview.enums.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnumMapperValue implements EnumMapperType {

    private final String code;
    private final String desc;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
