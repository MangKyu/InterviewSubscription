package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository <UserEntity, Long> {

    List<UserEntity> findAllByIsEnableTrue();

}
