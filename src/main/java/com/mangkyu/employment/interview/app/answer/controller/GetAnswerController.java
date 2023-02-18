package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAnswerController {

    private final GetAnswerService answerService;

    @GetMapping("/answers/{resourceId}")
    public ResponseEntity<GetAnswerResponse> getAnswer(@PathVariable final String resourceId) {
        return ResponseEntity.ok(answerService.getAnswer(resourceId));
    }

}