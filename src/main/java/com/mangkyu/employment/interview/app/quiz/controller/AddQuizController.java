package com.mangkyu.employment.interview.app.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequestHolder;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
class AddQuizController {

    private final QuizService quizService;
    private final ObjectMapper objectMapper;

    @GetMapping("/quizzes/init")
    public ResponseEntity<Void> initQuizListByFile() throws IOException {
        final File file = ResourceUtils.getFile("classpath:quiz/quiz.json");
        final AddQuizRequestHolder addQuizRequestHolder = objectMapper.readValue(file, AddQuizRequestHolder.class);

        addQuizRequestHolder.getQuizList().forEach(quizService::addQuiz);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Void> addQuiz(@RequestBody @Valid final AddQuizRequest addQuizRequest) {
        quizService.addQuiz(addQuizRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}