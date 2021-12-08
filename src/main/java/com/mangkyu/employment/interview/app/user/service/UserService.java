package com.mangkyu.employment.interview.app.user.service;

import com.mangkyu.employment.interview.app.user.entity.User;
import com.mangkyu.employment.interview.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void disableUser(final User user) {
        user.setIsEnable(false);
        userRepository.save(user);
    }

    public List<User> getEnabledUserList() {
        return userRepository.findAllByIsEnableTrue();
    }
}