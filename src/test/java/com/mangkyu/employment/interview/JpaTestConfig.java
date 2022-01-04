package com.mangkyu.employment.interview;


import com.mangkyu.employment.interview.config.querydsl.QueryDslConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import({QueryDslConfig.class})
@DataJpaTest
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaTestConfig {
}
