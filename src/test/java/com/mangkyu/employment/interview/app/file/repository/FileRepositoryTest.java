package com.mangkyu.employment.interview.app.file.repository;

import com.mangkyu.employment.interview.JpaTestConfig;
import com.mangkyu.employment.interview.app.file.entity.MyFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTestConfig
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void insertMyFile() {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final String fileName = "fileName";
        final MyFile myFile = MyFile.builder()
                .resourceId(resourceId)
                .fileName(fileName)
                .build();

        // when
        final MyFile result = fileRepository.save(myFile);

        // then
        assertThat(result.getResourceId()).isEqualTo(resourceId);
        assertThat(result.getFileName()).isEqualTo(fileName);
    }

    @Test
    public void findFileByResourceId() {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final String fileName = "fileName";
        final MyFile myFile = MyFile.builder()
                .resourceId(resourceId)
                .fileName(fileName)
                .build();

        fileRepository.save(myFile);

        // when
        final Optional<MyFile> optionalResult = fileRepository.findByResourceId(resourceId);

        // then
        assertThat(optionalResult.isPresent()).isTrue();
        final MyFile result = optionalResult.get();

        assertThat(result.getResourceId()).isEqualTo(resourceId);
        assertThat(result.getFileName()).isEqualTo(fileName);
    }

}