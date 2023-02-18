package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.DeleteAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class DeleteAnswerController {

    private final DeleteAnswerService answerService;

    @DeleteMapping("/answers/{resourceId}")
    public ResponseEntity<Void> delete(@PathVariable final String resourceId) {
        answerService.delete(resourceId);

        return ResponseEntity.noContent().build();
    }
}