package com.mangkyu.employment.interview.testutils;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

public final class EntityCreationUtils {

    public static Quiz quiz() {
        final Quiz quiz = Quiz.builder()
                .resourceId(UUID.randomUUID().toString())
                .title("quiz")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(List.of(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();
        ReflectionTestUtils.setField(quiz, "createdAt", LocalDateTime.now());

        return quiz;
    }

    public static Quiz quiz(final String title, final QuizCategory category, final List<QuizLevel> quizLevelList) {
        final Quiz quiz = Quiz.builder()
                .resourceId(UUID.randomUUID().toString())
                .title(title)
                .quizCategory(category)
                .quizLevel(quizLevelList)
                .build();
        ReflectionTestUtils.setField(quiz, "createdAt", LocalDateTime.now());

        return quiz;
    }

    public static Member member() {
        final Member member = Member.builder()
                .resourceId(UUID.randomUUID().toString())
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .solvedQuizList(List.of())
                .build();
        ReflectionTestUtils.setField(member, "createdAt", LocalDateTime.now());

        return member;
    }

    public static Member member(final int quizSize, final Set<QuizDay> daySet, final Set<QuizCategory> categorySet) {
        final Member member = Member.builder()
                .resourceId( UUID.randomUUID().toString())
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .solvedQuizList(List.of())
                .quizSize(quizSize)
                .quizDaySet(daySet)
                .quizCategorySet(categorySet)
                .build();
        ReflectionTestUtils.setField(member, "createdAt", LocalDateTime.now());

        return member;
    }

    public static SolvedQuiz solvedQuiz(final Quiz quiz, final Member member) {
        return SolvedQuiz.builder()
                .quiz(quiz)
                .member(member)
                .build();
    }

    public static Answer answer(final Quiz quiz) {
        final Answer answer = Answer.builder()
                .resourceId(UUID.randomUUID().toString())
                .quiz(quiz)
                .description("desc")
                .build();

        ReflectionTestUtils.setField(answer, "createdAt", LocalDateTime.now());
        return answer;
    }

}
