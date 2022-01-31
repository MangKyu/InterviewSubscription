package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory implements EnumMapperType {

    JAVA("Java", "Java 언어와 관련된 면접 질문을 살펴보세요", true),
    SPRING("Spring Framework", "Spring 프레임워크와 관련된 면접 질문을 살펴보세요", true),
    SERVER("Server Programming", "서버 프로그래밍 관련된 면접 질문을 살펴보세요", true),
    NETWORK("Network", "네트워크와 관련된 면접 질문을 살펴보세요", true),
    OPERATING_SYSTEM("Operating System", "운영체제와 관련된 면접 질문을 살펴보세요", true),
    DATABASE("Database", "데이터베이스와 관련된 면접 질문을 살펴보세요", true),
    PROGRAMMING("Overall Programming", "전반적인 프로그래밍과 관련된 면접 질문을 살펴보세요", true),
    DATA_STRUCTURE("Data Structure", "자료구조와 관련된 면접 질문을 살펴보세요", true),
    ALGORITHM("Algorithm", "알고리즘과 관련된 면접 질문을 살펴보세요", true),
    PROBLEM_SOLVING("Problem Solving", "문제 해결과 관련된 면접 질문을 살펴보세요", true),
    CULTURE("Culture Fit", "문화 적합도와 관련된 면접 질문을 살펴보세요", false),
    EXPERIENCE("Personal Experience", "개인의 경험와 관련된 면접 질문을 살펴보세요", false),
    PERSONALITY("Personality", "개인의 성향과 관련된 면접 질문을 살펴보세요", false),
    ;

    private final String title;
    private final String desc;
    private final boolean expose;

}
