package com.mangkyu.employment.interview.enums.value;

import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory implements EnumMapperType {

    JAVA("Java", "Java 언어와 관련된 면접 질문을 살펴보세요"),
    SPRING("Spring Framework", "Spring 프레임워크와 관련된 면접 질문을 살펴보세요"),
    SERVER("Server Programming", "서버 프로그래밍 관련된 면접 질문을 살펴보세요"),
    NETWORK("Network", "네트워크와 관련된 면접 질문을 살펴보세요"),
    OPERATING_SYSTEM("Operating System", "운영체제와 관련된 면접 질문을 살펴보세요"),
    DATABASE("Database", "데이터베이스와 관련된 면접 질문을 살펴보세요"),
    PROGRAMMING("Overall Programming", "전반적인 프로그래밍과 관련된 면접 질문을 살펴보세요"),
    DATA_STRUCTURE("Data Structure", "자료구조와 관련된 면접 질문을 살펴보세요"),
    ALGORITHM("Algorithm", "알고리즘과 관련된 면접 질문을 살펴보세요"),
    PROBLEM_SOLVING("Problem Solving", "문제 해결과 관련된 면접 질문을 살펴보세요"),
    CULTURE("Culture Fit", "문화 적합도와 관련된 면접 질문을 살펴보세요"),
    EXPERIENCE("Personal Experience", "개인의 경험와 관련된 면접 질문을 살펴보세요"),
    PERSONALITY("Personality", "개인의 성향과 관련된 면접 질문을 살펴보세요"),
    ;

    private final String title;
    private final String desc;

}
