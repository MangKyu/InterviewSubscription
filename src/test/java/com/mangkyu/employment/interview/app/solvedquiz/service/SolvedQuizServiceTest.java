package com.mangkyu.employment.interview.app.solvedquiz.service;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.app.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SolvedQuizServiceTest {

    @InjectMocks
    private SolvedQuizService target;

    @Mock
    private SolvedQuizRepository solvedQuizRepository;

    @Test
    public void addSolvedQuizListSuccess() {
        // given
        final User user = user();
        final List<Quiz> quizList = quizList();

        // when
        target.addSolvedQuizList(user, quizList);

        // then
        
        // verify
    }

    private User user() {
        return User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();
    }

    private List<Quiz> quizList() {
        return Arrays.asList(
                quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR)),
                quiz(QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR)),
                quiz(QuizCategory.SPRING, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR, QuizLevel.NEW))
        );
    }

    private Quiz quiz(final QuizCategory quizCategory, final List<QuizLevel> quizLevelList) {
        return Quiz.builder()
                .quizCategory(quizCategory)
                .quizLevel(quizLevelList)
                .title("title").build();
    }

}