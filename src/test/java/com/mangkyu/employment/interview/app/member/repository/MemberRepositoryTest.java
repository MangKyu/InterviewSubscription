package com.mangkyu.employment.interview.app.member.repository;

import com.mangkyu.employment.interview.JpaTestConfig;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTestConfig
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void selectMemberListByCycle() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final Member member = EntityCreationUtils.member(5, quizDaySet, quizCategorySet);

        final Member savedMember = memberRepository.save(member);
        memberRepository.save(savedMember);

        // when
        final List<Member> dailyResult = memberRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.MONDAY);
        final List<Member> regularResult = memberRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.THURSDAY);

        // then
        assertThat(dailyResult.size()).isOne();
        assertThat(regularResult.size()).isZero();
    }

    @Test
    public void insertMember() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final Member member = EntityCreationUtils.member(5, quizDaySet, quizCategorySet);

        // when
        final Member result = memberRepository.save(member);

        // then
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
        assertThat(result.getQuizLevel()).isEqualTo(member.getQuizLevel());
        assertThat(result.getQuizSize()).isEqualTo(member.getQuizSize());
    }

    @Test
    public void updateMemberDisabled() {
        // given
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.FRIDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        final Member member = EntityCreationUtils.member(3, quizDaySet, quizCategorySet);

        final Member savedMember = memberRepository.save(member);
        savedMember.setIsEnable(false);
        memberRepository.save(savedMember);

        // when
        final List<Member> result = memberRepository.findAllByIsEnableTrueAndQuizDaySetIs(QuizDay.MONDAY);

        // then
        assertThat(result.size()).isZero();
    }

}