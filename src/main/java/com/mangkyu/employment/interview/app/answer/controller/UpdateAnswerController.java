package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import com.mangkyu.employment.interview.app.answer.service.UpdateAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateAnswerController {

    private final UpdateAnswerService answerService;

    @PutMapping("/answers")
    public ResponseEntity<Void> putAnswer(@RequestBody @Valid final AddAnswerRequest addAnswerRequest) {
        answerService.addAnswer(addAnswerRequest);
        return ResponseEntity.noContent()
                .build();
    }

}