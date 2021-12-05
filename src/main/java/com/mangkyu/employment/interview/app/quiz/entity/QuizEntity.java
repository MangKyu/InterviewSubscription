package com.mangkyu.employment.interview.app.quiz.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class QuizEntity extends BaseEntity {

    private String title;

    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @ElementCollection(targetClass = QuizLevel.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<QuizLevel> quizLevel;

}
