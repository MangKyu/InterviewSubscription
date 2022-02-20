package com.mangkyu.employment.interview.app.solvedquiz.converter;

import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolvedQuizDtoConverterTest {

    @Test
    public void convertMemberAndQuizToSolvedQuizEntity() {
        // given
        final Member member = EntityCreationUtils.member();
        final Quiz quiz = EntityCreationUtils.quiz();

        // when
        final SolvedQuiz result = SolvedQuizDtoConverter.convert(member, quiz);

        // then
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getQuiz()).isEqualTo(quiz);
    }

}