package com.mangkyu.employment.interview.app.answer.repository;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void insertAnswer() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Quiz savedQuiz = quizRepository.save(quiz);
        final Answer answer = EntityCreationUtils.answer(savedQuiz);

        answerRepository.save(answer);
        savedQuiz.setAnswer(answer);
        entityManager.flush();

        // then
        final Answer result = answerRepository.save(answer);

        assertThat(result.getResourceId()).isEqualTo(answer.getResourceId());
        assertThat(result.getDesc()).isEqualTo(answer.getDesc());
        assertThat(result.getQuiz()).isEqualTo(quiz);
    }

    @Test
    public void selectAnswerByQuizId() {
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
        assertThat(result.get().getDesc()).isEqualTo(answer.getDesc());
        assertThat(optionalQuizResult.isPresent()).isTrue();
        assertThat(optionalQuizResult.get().getAnswer()).isNotNull();
        assertThat(optionalQuizResult.get().getAnswer().getResourceId()).isEqualTo(saveAnswer.getResourceId());
    }

}