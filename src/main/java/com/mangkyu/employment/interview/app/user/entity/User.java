package com.mangkyu.employment.interview.app.user.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User extends BaseEntity {

    private String email;

    @Enumerated(EnumType.STRING)
    private QuizLevel quizLevel;

    @OneToMany(mappedBy = "user")
    private List<SolvedQuiz> solvedQuizList = new ArrayList<>();

}
