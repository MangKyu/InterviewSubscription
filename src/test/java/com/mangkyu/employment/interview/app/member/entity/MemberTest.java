package com.mangkyu.employment.interview.app.member.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void member비활성화() {
        // given
        Member member = Member.builder().build();

        // when
        member.disableUser();

        // then
        assertThat(member.getIsEnable()).isFalse();
    }

}