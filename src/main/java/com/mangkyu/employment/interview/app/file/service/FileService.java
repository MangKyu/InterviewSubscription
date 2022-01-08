package com.mangkyu.employment.interview.app.file.service;

import com.mangkyu.employment.interview.app.common.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.app.common.erros.exception.QuizException;
import com.mangkyu.employment.interview.app.file.dto.FileUploadResponse;
import com.mangkyu.employment.interview.app.file.entity.MyFile;
import com.mangkyu.employment.interview.app.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.mangkyu.employment.interview.app.file.constants.FileConstants.FILE_API_PREFIX;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.directory}")
    private String fileDirectory;

    @PostConstruct
    public void init() throws IOException {
        if (StringUtils.isBlank(fileDirectory)) {
            throw new QuizException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        final Path fileDirectoryPath = Paths.get(fileDirectory);
        if (Files.notExists(fileDirectoryPath)) {
            Files.createDirectory(fileDirectoryPath);
        }
    }

    public FileUploadResponse upload(final MultipartFile multipartFile) {
        try {
            final String resourceId = UUID.randomUUID().toString();
            final String fileName = generateFileName(multipartFile.getOriginalFilename(), resourceId);
            final File file = new File(fileDirectory + fileName);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

            final MyFile myFile = MyFile.builder()
                    .resourceId(resourceId)
                    .fileName(fileName)
                    .build();

            fileRepository.save(myFile);

            return FileUploadResponse.uploadSuccessResponse(FILE_API_PREFIX + "/" + resourceId);
        } catch (final IOException e) {
            log.error("File Upload Fail", e);
            return FileUploadResponse.uploadFailResponse();
        }
    }

    private String generateFileName(final String originalFileName, final String uuid) {
        final String fileExtension = FilenameUtils.getExtension(originalFileName);
        return uuid + FilenameUtils.EXTENSION_SEPARATOR + fileExtension;
    }

    public Resource getFileAsResource(final String resourceId) {
        final MyFile myFile = fileRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new QuizException(CommonErrorCode.RESOURCE_NOT_FOUND));

        try {
            final Path path = Paths.get(fileDirectory + myFile.getFileName());
            return new ByteArrayResource(Files.readAllBytes(path));
        } catch (final IOException e) {
            log.error("getFileAsResource Fail", e);
            throw new QuizException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
