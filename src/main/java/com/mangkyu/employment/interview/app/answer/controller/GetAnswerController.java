package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.service.GetAnswerService;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetAnswerController {

    private final GetAnswerService answerService;

    @GetMapping("/answers/{resourceId}")
    public ResponseEntity<GetAnswerResponse> get(@PathVariable final String resourceId) {
        Answer answer = answerService.get(resourceId);
        return ResponseEntity.ok(QuizDtoConverter.convert(answer));
    }

}