package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertUser() {
        // given
        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizSize(5)
                .build();

        // when
        final User result = userRepository.save(user);

        // then
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getQuizLevel()).isEqualTo(user.getQuizLevel());
        assertThat(result.getQuizSize()).isEqualTo(user.getQuizSize());
    }

    @Test
    public void updateUserDisabled() {
        // given
        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        final User savedUser = userRepository.save(user);
        savedUser.setIsEnable(false);
        userRepository.save(savedUser);

        // when
        final List<User> result = userRepository.findAllByIsEnableTrue();

        // then
        assertThat(result.size()).isZero();
    }

}