package com.mangkyu.employment.interview.app.member.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mangkyu.employment.interview.app.quiz.constants.QuizConstants.DEFAULT_QUIZ_SIZE;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String resourceId;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private QuizLevel quizLevel;

    @ElementCollection(targetClass = QuizDay.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<QuizDay> quizDaySet;

    @ElementCollection(targetClass = QuizCategory.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<QuizCategory> quizCategorySet;

    @Builder.Default
    private Integer quizSize = DEFAULT_QUIZ_SIZE;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<SolvedQuiz> solvedQuizList = new ArrayList<>();

    public void disableUser() {
        setIsEnable(false);
    }
}
