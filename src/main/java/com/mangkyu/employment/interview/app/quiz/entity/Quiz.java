package com.mangkyu.employment.interview.app.quiz.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Quiz extends BaseEntity {

    private String title;

    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @ElementCollection(targetClass = QuizLevel.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<QuizLevel> quizLevel;

    @Builder.Default
    @OneToMany(mappedBy = "quiz")
    private List<SolvedQuiz> solvedQuizList = new ArrayList<>();

}
