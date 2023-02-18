package com.mangkyu.employment.interview.app.answer.repository;

import com.mangkyu.employment.interview.JpaTestConfig;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTestConfig
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void findOptionalByQuizId() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Quiz savedQuiz = quizRepository.save(quiz);
        final Answer answer = EntityCreationUtils.answer(savedQuiz);

        final Answer saveAnswer = answerRepository.save(answer);
        savedQuiz.setAnswer(answer);
        entityManager.flush();

        // then
        final Optional<Answer> result = answerRepository.findByResourceId(answer.getResourceId());
        final Optional<Quiz> optionalQuizResult = quizRepository.findById(savedQuiz.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getDescription()).isEqualTo(answer.getDescription());
        assertThat(optionalQuizResult.isPresent()).isTrue();
        assertThat(optionalQuizResult.get().getAnswer()).isNotNull();
        assertThat(optionalQuizResult.get().getAnswer().getResourceId()).isEqualTo(saveAnswer.getResourceId());
    }

}