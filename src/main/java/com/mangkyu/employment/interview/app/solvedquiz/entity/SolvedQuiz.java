package com.mangkyu.employment.interview.app.solvedquiz.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "solved_quiz")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SolvedQuiz extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}
