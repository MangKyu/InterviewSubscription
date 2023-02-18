package com.mangkyu.employment.interview.app.answer.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String resourceId;

    @OneToOne(mappedBy = "answer")
    private Quiz quiz;

    @Setter
    @Lob
    @Column(length = 5000)
    private String description;

}