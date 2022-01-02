package com.mangkyu.employment.interview.app.quiz.entity;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Quiz extends BaseEntity {

    @Column(nullable = false)
    private String resourceId;

    private String title;

    @Enumerated(EnumType.STRING)
    private QuizCategory quizCategory;

    @ElementCollection(targetClass = QuizLevel.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<QuizLevel> quizLevel;

//    @Setter
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "answer_id")
//    private Answer answer;

}