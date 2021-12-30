package com.mangkyu.employment.interview.enums.factory;

import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EnumMapperFactory {

    private final Map<EnumMapperKey, List<EnumMapperValue>> factory;

    public List<EnumMapperValue> get(final EnumMapperKey key) {
        return factory.get(key);
    }

    public EnumMapperValue getElement(final EnumMapperKey key, final EnumMapperType enumMapperType) {
        // TODO(MinKyu): Add Exception Handling
        return factory.get(key).stream()
                .filter(v -> v.getCode().equals(enumMapperType.name()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void put(final EnumMapperKey key) {
        factory.put(key, createEnumMapperValueList(key.getEnumClass()));
    }

    private List<EnumMapperValue> createEnumMapperValueList(final Class<? extends EnumMapperType> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(this::convertToEnumMapperValue)
                .collect(Collectors.toList());
    }

    private EnumMapperValue convertToEnumMapperValue(final EnumMapperType enumMapperType) {
        return EnumMapperValue.builder()
                .code(enumMapperType.name())
                .title(enumMapperType.getTitle())
                .desc(enumMapperType.getDesc())
                .build();
    }

}
