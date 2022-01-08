package com.mangkyu.employment.interview.app.file.service;

import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import com.mangkyu.employment.interview.app.file.dto.FileUploadResponse;
import com.mangkyu.employment.interview.app.file.entity.MyFile;
import com.mangkyu.employment.interview.app.file.repository.FileRepository;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService target;

    @Mock
    private FileRepository fileRepository;

    @Test
    public void initFail_fileDirectoryEmpty() {
        // given

        // when
        final RestApiException result = assertThrows(RestApiException.class, () -> target.init());

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void initSuccess_fileDirectoryExists() throws IOException {
        // given
        ReflectionTestUtils.setField(target, "fileDirectory", "/");

        // when
        target.init();

        // then
    }

    @Test
    public void initSuccess_createFileDirectory() throws IOException {
        // given
        final Path currentPath = Paths.get("");
        final String testDirectory = currentPath.toAbsolutePath() + "/temp" + UUID.randomUUID() + "/";
        FileUtils.deleteDirectory(new File(testDirectory));
        ReflectionTestUtils.setField(target, "fileDirectory", testDirectory);

        // when
        target.init();

        // then
        FileUtils.deleteDirectory(new File(testDirectory));
    }

    @Test
    public void fileUploadFail_IOException() throws IOException {
        // given
        final Path currentPath = Paths.get("");
        final String testDirectory = currentPath.toAbsolutePath() + "/temp" + UUID.randomUUID() + "/";
        FileUtils.deleteDirectory(new File(testDirectory));
        ReflectionTestUtils.setField(target, "fileDirectory", testDirectory);

        final MultipartFile multipartFile = mock(MultipartFile.class);
        doThrow(new IOException()).when(multipartFile).getInputStream();

        // when
        final FileUploadResponse result = target.upload(multipartFile);

        // then
        assertThat(result.isUploaded()).isFalse();

        // then
        FileUtils.deleteDirectory(new File(testDirectory));
    }

    @Test
    public void fileUploadSuccess() throws IOException {
        // given
        final Path currentPath = Paths.get("");
        final String fileName = "fileName";
        final MultipartFile multipartFile = new MockMultipartFile(fileName, new byte[]{});

        final String testDirectory = currentPath.toAbsolutePath() + "/temp" + UUID.randomUUID() + "/";
        ReflectionTestUtils.setField(target, "fileDirectory", testDirectory);
        FileUtils.deleteDirectory(new File(testDirectory));

        // when
        final FileUploadResponse result = target.upload(multipartFile);

        // then
        assertThat(result.isUploaded()).isTrue();
        assertThat(result.getUrl()).isNotNull();

        // then
        FileUtils.deleteDirectory(new File(testDirectory));
    }

    @Test
    public void getFileAsResourceFail_MyFileNotExists() {
        // given
        final String resourceId = UUID.randomUUID().toString();

        doReturn(Optional.empty()).when(fileRepository).findByResourceId(resourceId);

        // when
        final RestApiException result = assertThrows(RestApiException.class, () -> target.getFileAsResource(resourceId));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void getFileAsResourceFail_IOException() throws IOException {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final Path currentPath = Paths.get("");
        final MultipartFile multipartFile = new MockMultipartFile(resourceId, new byte[]{});

        final String testDirectory = currentPath.toAbsolutePath() + "/temp" + resourceId + "/";
        FileUtils.deleteDirectory(new File(testDirectory));
        ReflectionTestUtils.setField(target, "fileDirectory", testDirectory);

        final File file = new File(testDirectory + resourceId);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

        final MyFile myFile = MyFile.builder()
                .resourceId(resourceId)
                .fileName(resourceId)
                .build();


        doReturn(Optional.of(myFile)).when(fileRepository).findByResourceId(resourceId);

        // when
        final Resource result = target.getFileAsResource(resourceId);

        // then
        assertThat(result).isNotNull();
        FileUtils.deleteDirectory(new File(testDirectory));
    }

}