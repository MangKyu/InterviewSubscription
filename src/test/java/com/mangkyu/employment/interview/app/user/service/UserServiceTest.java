package com.mangkyu.employment.interview.app.user.service;

import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.user.dto.AddUserRequest;
import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService target;

    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper modelMapper;

    private final DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

    @BeforeEach
    public void init() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }

    @Test
    public void addUserSuccess() {
        // given
        final Set<DayOfWeek> quizDaySet = new HashSet<>();
        quizDaySet.add(DayOfWeek.MONDAY);
        quizDaySet.add(DayOfWeek.WEDNESDAY);
        quizDaySet.add(DayOfWeek.SATURDAY);

        final AddUserRequest addUserRequest = AddUserRequest.builder()
                .email("whalsrb1226@gmail.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
                .build();

        // when
        target.addUser(addUserRequest);

        // then
    }

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
        doReturn(enabledUserList).when(userRepository).findAllByIsEnableTrueAndQuizDaySetIs(dayOfWeek);

        // when
        final List<User> result = target.getEnabledUserList(dayOfWeek);

        // then
        assertThat(result.size()).isEqualTo(enabledUserList.size());

        // verify
        verify(userRepository, times(1)).findAllByIsEnableTrueAndQuizDaySetIs(dayOfWeek);
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