package com.mangkyu.employment.interview.app.quiz.enums;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizLevel implements EnumMapperType {

	NEW("New Developers"),
	JUNIOR("Junior Developers"),
	SENIOR("Senior Developers"),
	;

	private final String desc;

	@Override
	public String getCode() {
		return name();
	}
}
