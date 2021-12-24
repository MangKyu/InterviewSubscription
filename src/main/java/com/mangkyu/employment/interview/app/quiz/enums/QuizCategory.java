package com.mangkyu.employment.interview.app.quiz.enums;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory implements EnumMapperType {

	JAVA("Java"),
	SPRING("Spring Framework"),
	SERVER("Server Side Programming"),
	NETWORK("Network"),
	OPERATING_SYSTEM("Operating System"),
	DATABASE("Database"),
	PROGRAMMING("Overall Programming"),
	DATA_STRUCTURE("Data Structure"),
	ALGORITHM("Database"),
	PROBLEM_SOLVING("Problem Solving"),
	CULTURE("Culture Fit"),
	EXPERIENCE("Personal Experience"),
	PERSONALITY("About Interviewee"),
	;

	private final String desc;

	@Override
	public String getCode() {
		return name();
	}
}
