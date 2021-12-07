package com.mangkyu.employment.interview.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    public void readFileText() throws IOException {
        final String fileText = FileUtils.readFileText("classpath:templates/startMailFormat.html");
        assertThat(fileText).isNotNull();
    }

}