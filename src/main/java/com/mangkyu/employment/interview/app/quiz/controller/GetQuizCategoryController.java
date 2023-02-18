package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.dto.QuizCategoryResponseHolder;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetQuizCategoryController {

    private final QuizService quizService;

    @GetMapping("/quizzes/categories")
    public ResponseEntity<QuizCategoryResponseHolder> getQuizCategoryList() {
        final QuizCategoryResponseHolder response = QuizCategoryResponseHolder.builder()
                .categoryList(quizService.getQuizCategoryList())
                .build();
        return ResponseEntity.ok(response);
    }

}