package com.mangkyu.employment.interview.utils;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    public void getCurrentDate() {
        final String currentDate = DateUtils.getCurrentDate();
        System.out.println(currentDate);
    }

}