package com.mangkyu.employment.interview.app.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizLevel {

	NEW("New Developers"),
	JUNIOR("Junior Developers"),
	SENIOR("Senior Developers"),
	;

	private final String desc;

}
