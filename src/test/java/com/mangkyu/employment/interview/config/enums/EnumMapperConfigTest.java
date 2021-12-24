package com.mangkyu.employment.interview.config.enums;

import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EnumMapperConfigTest {

    @Test
    public void enumMapperFactoryCreated() {
        // given
        final EnumMapperFactory result = new EnumMapperConfig().enumMapperFactory();

        // when

        // then
        assertThat(result).isNotNull();
    }

}