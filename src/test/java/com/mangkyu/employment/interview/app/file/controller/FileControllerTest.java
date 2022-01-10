package com.mangkyu.employment.interview.app.file.controller;

import com.google.gson.Gson;
import com.mangkyu.employment.interview.app.enums.controller.EnumsController;
import com.mangkyu.employment.interview.app.file.dto.FileUploadResponse;
import com.mangkyu.employment.interview.app.file.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.mangkyu.employment.interview.app.file.constants.FileConstants.FILE_API_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void uploadFileSuccess() throws Exception {
        // given
        final MockMultipartFile multipartFile = new MockMultipartFile("upload", new byte[0]);
        final FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
                .uploaded(false)
                .build();

        doReturn(fileUploadResponse).when(fileService).upload(multipartFile);

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.multipart(FILE_API_PREFIX)
                        .file(multipartFile)
        );

        // then
        final ResultActions resultActions = result.andExpect(status().isOk());
        final String stringResponse = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        final FileUploadResponse responseResult = new Gson().fromJson(stringResponse, FileUploadResponse.class);

        assertThat(responseResult.isUploaded()).isFalse();
    }

    @Test
    public void getResource() throws Exception {
        // given
        final String resourceId = UUID.randomUUID().toString();
        final String url = FILE_API_PREFIX + "/" + resourceId;
        final Resource resource = new ByteArrayResource(new byte[0]);

        doReturn(resource).when(fileService).getFileAsResource(resourceId);

        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        result.andExpect(status().isOk());
    }

}