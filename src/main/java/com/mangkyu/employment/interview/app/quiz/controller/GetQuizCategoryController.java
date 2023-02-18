package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
class GetQuizCategoryController {

    private final QuizService quizService;

    @GetMapping("/quizzes/categories")
    public ResponseEntity<Map<String, List<QuizCategoryResponse>>> getQuizCategoryList() {
        return ResponseEntity.ok(Map.of("categoryList", quizService.getQuizCategoryList()));
    }

}