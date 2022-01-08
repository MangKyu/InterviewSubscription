package com.mangkyu.employment.interview.app.answer.controller;

import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.service.AnswerService;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PutMapping("/answer")
    public ResponseEntity<Void> putAnswer(@RequestBody @Valid final AddAnswerRequest addAnswerRequest) throws RestApiException {
        answerService.addAnswer(addAnswerRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/answer")
    public ResponseEntity<Void> postAnswer(@RequestBody @Valid final AddAnswerRequest addAnswerRequest) throws RestApiException {
        answerService.addAnswer(addAnswerRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/answer/{resourceId}")
    public ResponseEntity<GetAnswerResponse> getAnswer(@PathVariable final String resourceId) throws RestApiException {
        return ResponseEntity.ok(answerService.getAnswer(resourceId));
    }

    @DeleteMapping("/answer/{resourceId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable final String resourceId) throws RestApiException {
        answerService.deleteAnswer(resourceId);

        return ResponseEntity.noContent().build();
    }
}