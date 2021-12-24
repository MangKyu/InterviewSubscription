package com.mangkyu.employment.interview.enums.factory;

import com.mangkyu.employment.interview.config.enums.EnumMapperConfig;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EnumMapperFactoryTest {

    private EnumMapperFactory target;

    @BeforeEach
    public void init() {
        target = new EnumMapperConfig().enumMapperFactory();
    }

    @Test
    public void getQuizCategoryList() {
        // given
        final EnumMapperKey enumMapperKey = EnumMapperKey.QUIZ_CATEGORY;

        // when
        final List<EnumMapperValue> result = target.get(enumMapperKey);

        // then
        assertThat(result.size()).isEqualTo(enumMapperKey.getEnumClass().getEnumConstants().length);
    }

    @Test
    public void getQuizLevelList() {
        // given
        final EnumMapperKey enumMapperKey = EnumMapperKey.QUIZ_LEVEL;

        // when
        final List<EnumMapperValue> result = target.get(enumMapperKey);

        // then
        assertThat(result.size()).isEqualTo(enumMapperKey.getEnumClass().getEnumConstants().length);
    }

}