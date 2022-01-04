package com.mangkyu.employment.interview.config.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class QueryDslConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(final EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
