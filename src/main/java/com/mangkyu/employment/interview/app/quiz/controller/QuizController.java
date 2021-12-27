package com.mangkyu.employment.interview.app.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequestHolder;
import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponse;
import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponseHolder;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quiz/init")
    public ResponseEntity<Void> initQuizListByFile() throws IOException {
        final File file = ResourceUtils.getFile("classpath:quiz/quiz.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final AddQuizRequestHolder addQuizRequestHolder = objectMapper.readValue(file, AddQuizRequestHolder.class);

        addQuizRequestHolder.getQuizList().forEach(quizService::addQuiz);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/quiz")
    public ResponseEntity<Void> addQuiz(@RequestBody @Valid final AddQuizRequest addQuizRequest) {
        quizService.addQuiz(addQuizRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Void> addQuiz(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/quiz/categories")
    public ResponseEntity<QuizCategoryResponseHolder> getQuizCategoryList() {
        final QuizCategoryResponseHolder response = QuizCategoryResponseHolder.builder()
                .categoryList(quizService.getQuizCategoryList())
                .build();
        return ResponseEntity.ok(response);
    }

}
