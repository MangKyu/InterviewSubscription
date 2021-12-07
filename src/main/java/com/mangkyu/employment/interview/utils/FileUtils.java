package com.mangkyu.employment.interview.utils;

import lombok.NoArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public final class FileUtils {

    public static String readFileText(final String filePath) throws IOException {
        final File file = ResourceUtils.getFile(filePath);
        final InputStream inputStream = new FileInputStream(file);
        final byte[] byteData = FileCopyUtils.copyToByteArray(inputStream);
        return new String(byteData, StandardCharsets.UTF_8);
    }

}
