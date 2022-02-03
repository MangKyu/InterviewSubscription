package com.mangkyu.employment.interview.app.solvedquiz.repository;

import com.mangkyu.employment.interview.JpaTestConfig;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTestConfig
class SolvedQuizRepositoryTest {

    @Autowired
    private SolvedQuizRepository solvedQuizRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertQuiz() {
        // given
        final Member member = EntityCreationUtils.member();
        final Quiz quiz = EntityCreationUtils.quiz();

        final Quiz quizResult = quizRepository.save(quiz);
        final Member memberResult = memberRepository.save(member);

        final SolvedQuiz solvedQuiz = EntityCreationUtils.solvedQuiz(quiz, member);

        // when
        final SolvedQuiz result = solvedQuizRepository.save(solvedQuiz);

        // then
        assertThat(result.getQuiz()).isEqualTo(quizResult);
        assertThat(result.getMember()).isEqualTo(memberResult);
    }

}