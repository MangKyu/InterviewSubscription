package com.mangkyu.employment.interview.enums.factory;

import com.mangkyu.employment.interview.config.enums.EnumMapperConfig;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EnumMapperFactoryTest {

    private EnumMapperFactory target;

    @BeforeEach
    public void init() {
        target = new EnumMapperConfig().enumMapperFactory();
    }

    @Test
    public void getQuizCategoryElementFail_NotExists() {
        // given
        final EnumMapperKey enumMapperKey = EnumMapperKey.QUIZ_CATEGORY;

        // when
        final IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> target.getElement(enumMapperKey, QuizLevel.NEW));

        // then
        assertThat(result.getMessage()).isNull();
    }

    @Test
    public void getQuizCategoryElementSuccess() {
        // given
        final QuizCategory category = QuizCategory.JAVA;
        final EnumMapperKey enumMapperKey = EnumMapperKey.QUIZ_CATEGORY;

        // when
        final EnumMapperValue result = target.getElement(enumMapperKey, category);

        // then
        assertThat(result.getCode()).isEqualTo(category.name());
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