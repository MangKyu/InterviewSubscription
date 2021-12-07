package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.entity.UserEntity;
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
        final UserEntity userEntity = UserEntity.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        // when
        final UserEntity result = userRepository.save(userEntity);

        // then
        assertThat(result.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.getQuizLevel()).isEqualTo(userEntity.getQuizLevel());
    }

    @Test
    public void updateUserDisabled() {
        // given
        final UserEntity userEntity = UserEntity.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();

        final UserEntity savedUserEntity = userRepository.save(userEntity);
        savedUserEntity.setIsEnable(false);
        userRepository.save(savedUserEntity);

        // when
        final List<UserEntity> result = userRepository.findAllByIsEnableTrue();

        // then
        assertThat(result.size()).isZero();
    }

}