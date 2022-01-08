package com.mangkyu.employment.interview.app.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class FileUploadResponse {

    private final boolean uploaded;
    private final String url;

    public static FileUploadResponse uploadSuccessResponse(final String url) {
        return FileUploadResponse.builder()
                .uploaded(true)
                .url(url)
                .build();
    }

    public static FileUploadResponse uploadFailResponse() {
        return FileUploadResponse.builder()
                .uploaded(false)
                .build();
    }

}
