package com.mangkyu.employment.interview.app.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory {

	JAVA("Java"),
	SPRING("Spring Framework"),
	TDD("Test Driven Development"),
	DDD("Domain Driven Design"),
	INFRA("Infrastructure"),
	NETWORK("Network"),
	;

	private final String desc;

}
