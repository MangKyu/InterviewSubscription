package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void selectUserListByCycle() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizSize(5)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
                .build();

        final User savedUser = userRepository.save(user);
        userRepository.save(savedUser);

        // when
        final List<User> dailyResult = userRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.MONDAY);
        final List<User> regularResult = userRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.THURSDAY);

        // then
        assertThat(dailyResult.size()).isOne();
        assertThat(regularResult.size()).isZero();
    }

    @Test
    public void insertUser() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizSize(5)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
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
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .quizCategorySet(quizCategorySet)
                .build();

        final User savedUser = userRepository.save(user);
        savedUser.setIsEnable(false);
        userRepository.save(savedUser);

        // when
        final List<User> result = userRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.MONDAY);

        // then
        assertThat(result.size()).isZero();
    }

}