package com.mangkyu.employment.interview.app.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.common.pagination.CursorPageable;
import com.mangkyu.employment.interview.app.quiz.dto.*;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

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
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable final long id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<CursorPageable<GetQuizResponseHolder>> getQuizList(@Valid final GetQuizRequest getQuizRequest) {
        final CursorPageable<GetQuizResponseHolder> response = quizService.getQuizList(getQuizRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/categories")
    public ResponseEntity<QuizCategoryResponseHolder> getQuizCategoryList() {
        final QuizCategoryResponseHolder response = QuizCategoryResponseHolder.builder()
                .categoryList(quizService.getQuizCategoryList())
                .build();
        return ResponseEntity.ok(response);
    }

}