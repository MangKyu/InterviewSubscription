package com.mangkyu.employment.interview.app.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserQuizCycle {

	DAILY("Quiz Everyday"),
	REGULAR_INTERVALS("Quiz Regular Intervals"),
	;

	private final String desc;

}
