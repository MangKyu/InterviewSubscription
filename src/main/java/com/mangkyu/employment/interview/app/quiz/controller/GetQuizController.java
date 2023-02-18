package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.entity.PagingQuizzes;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

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
        PagingQuizzes quizzes = quizService.getQuizList(getQuizRequest);
        final GetQuizResponseHolder response = toResponse(quizzes, getQuizRequest.getCategory());
        return ResponseEntity.ok(response);
    }

    private GetQuizResponseHolder toResponse(PagingQuizzes quizzes, QuizCategory category) {
        return GetQuizResponseHolder.builder()
                .quizList(quizzes.getQuizzes().getQuizList().stream().map(QuizDtoConverter::convert).collect(Collectors.toList()))
                .category(enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, category))
                .hasNext(quizzes.isHasNext())
                .page(quizzes.getPageNumber())
                .size(quizzes.getPageSize())
                .totalPages(quizzes.getTotalPages())
                .build();
    }

    @GetMapping("/quizzes/search")
    public ResponseEntity<GetQuizResponseHolder> searchQuizList(@Valid final SearchQuizListRequest request) {
        final GetQuizResponseHolder response = quizService.searchQuizList(request);
        return ResponseEntity.ok(response);
    }

}