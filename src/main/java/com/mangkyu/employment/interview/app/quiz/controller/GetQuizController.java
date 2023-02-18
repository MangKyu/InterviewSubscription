package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class GetQuizController {

    private final GetQuizService quizService;
    private final EnumMapperFactory enumMapperFactory;

    @GetMapping("/quizzes/{resourceId}")
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable final String resourceId) {
        Quiz quiz = quizService.getQuiz(resourceId);
        return ResponseEntity.ok(
                QuizDtoConverter.convert(
                        quiz,
                        enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, quiz.getQuizCategory()))
        );
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