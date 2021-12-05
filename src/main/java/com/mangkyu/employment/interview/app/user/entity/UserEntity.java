package com.mangkyu.employment.interview.app.user.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    private String email;

    @Enumerated(EnumType.STRING)
    private QuizLevel quizLevel;

}
