package com.mangkyu.employment.interview.app.user.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.user.enums.UserQuizCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.mangkyu.employment.interview.app.quiz.constants.QuizConstants.DEFAULT_QUIZ_SIZE;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private QuizLevel quizLevel;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserQuizCycle userQuizCycle = UserQuizCycle.REGULAR_INTERVALS;

    @Builder.Default
    private Integer quizSize = DEFAULT_QUIZ_SIZE;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<SolvedQuiz> solvedQuizList = new ArrayList<>();

}
