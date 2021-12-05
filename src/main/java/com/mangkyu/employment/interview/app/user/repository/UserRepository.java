package com.mangkyu.employment.interview.app.user.repository;

import com.mangkyu.employment.interview.app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity, Long> {
}
