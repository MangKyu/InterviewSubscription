package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.AddAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class AddAnswerController {

    private final AddAnswerService answerService;

    @PostMapping("/answers")
    public ResponseEntity<Void> add(@RequestBody @Valid final AddAnswerRequest request) {
        answerService.add(request);
        return ResponseEntity.noContent()
                .build();
    }

}