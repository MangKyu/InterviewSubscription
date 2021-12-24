package com.mangkyu.employment.interview.app.user.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> quizDaySet;

    @Builder.Default
    private Integer quizSize = DEFAULT_QUIZ_SIZE;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<SolvedQuiz> solvedQuizList = new ArrayList<>();

}
