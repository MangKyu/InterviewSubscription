package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.dto.GetQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponseHolder;
import com.mangkyu.employment.interview.app.quiz.dto.SearchQuizListRequest;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class GetQuizController {

    private final QuizService quizService;

    @GetMapping("/quizzes/{resourceId}")
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable final String resourceId) {
        return ResponseEntity.ok(quizService.getQuiz(resourceId));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<GetQuizResponseHolder> getQuizList(@Valid final GetQuizRequest getQuizRequest) {
        final GetQuizResponseHolder response = quizService.getQuizList(getQuizRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quizzes/search")
    public ResponseEntity<GetQuizResponseHolder> searchQuizList(@Valid final SearchQuizListRequest request) {
        final GetQuizResponseHolder response = quizService.searchQuizList(request);
        return ResponseEntity.ok(response);
    }

}