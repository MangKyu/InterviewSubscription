package com.mangkyu.employment.interview.app.user.service;

import com.mangkyu.employment.interview.app.user.entity.UserEntity;
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
    public void disableUser(final UserEntity userEntity) {
        userEntity.setIsEnable(false);
        userRepository.save(userEntity);
    }

    public List<UserEntity> getEnabledUserList() {
        return userRepository.findAllByIsEnableTrue();
    }
}
