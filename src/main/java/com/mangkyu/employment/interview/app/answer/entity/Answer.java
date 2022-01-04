package com.mangkyu.employment.interview.app.answer.entity;

import com.mangkyu.employment.interview.app.answer.constants.AnswerConstants;
import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Lob
    @Column(length = AnswerConstants.MAX_ANSWER_SIZE)
    private String description;

}