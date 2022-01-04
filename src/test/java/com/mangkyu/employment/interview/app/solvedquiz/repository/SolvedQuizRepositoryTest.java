package com.mangkyu.employment.interview.app.solvedquiz.repository;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.repository.UserRepository;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SolvedQuizRepositoryTest {

    @Autowired
    private SolvedQuizRepository solvedQuizRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertQuiz() {
        // given
        final User user = EntityCreationUtils.user();
        final Quiz quiz = EntityCreationUtils.quiz();

        final Quiz quizResult = quizRepository.save(quiz);
        final User userResult = userRepository.save(user);

        final SolvedQuiz solvedQuiz = EntityCreationUtils.solvedQuiz(quiz, user);

        // when
        final SolvedQuiz result = solvedQuizRepository.save(solvedQuiz);

        // then
        assertThat(result.getQuiz()).isEqualTo(quizResult);
        assertThat(result.getUser()).isEqualTo(userResult);
    }

}