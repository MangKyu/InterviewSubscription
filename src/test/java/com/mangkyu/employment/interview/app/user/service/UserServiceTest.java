package com.mangkyu.employment.interview.app.user.service;

import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService target;

    @Mock
    private UserRepository userRepository;

    @Test
    public void disableUserSuccess() {
        // given
        final User user = user(true);

        // when
        target.disableUser(user);

        // then

        // verify
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getEnabledUserListSuccess() {
        // given
        final List<User> enabledUserList = Arrays.asList(user(true), user(true));
        doReturn(enabledUserList).when(userRepository).findAllByIsEnableTrue();

        // when
        final List<User> result = target.getEnabledUserList();

        // then
        assertThat(result.size()).isEqualTo(enabledUserList.size());

        // verify
        verify(userRepository, times(1)).findAllByIsEnableTrue();
    }

    private User user(final boolean isEnable) {
        final User user = User.builder()
                .email("minkyu@test.com")
                .quizLevel(QuizLevel.JUNIOR)
                .build();
        user.setIsEnable(isEnable);
        return user;
    }


}