package com.mangkyu.employment.interview.config.enums;

import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class EnumMapperConfig {

    @Bean
    public EnumMapperFactory enumMapperFactory() {
        final EnumMapperFactory enumMapperFactory = new EnumMapperFactory(new LinkedHashMap<>());

        enumMapperFactory.put(EnumMapperKey.QUIZ_LEVEL);
        enumMapperFactory.put(EnumMapperKey.QUIZ_CATEGORY);
        enumMapperFactory.put(EnumMapperKey.QUIZ_DAY);

        return enumMapperFactory;
    }

}
