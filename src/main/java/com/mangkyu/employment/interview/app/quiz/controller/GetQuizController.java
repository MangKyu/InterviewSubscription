package com.mangkyu.employment.interview.app.quiz.controller;

import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.entity.PagingQuizzes;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
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
        return ResponseEntity.ok(QuizDtoConverter.convert(quiz));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<GetPagingQuizResponse> getQuizList(@Valid final GetQuizRequest getQuizRequest) {
        final PagingQuizzes quizzes = quizService.getQuizList(getQuizRequest);
        return ResponseEntity.ok(
                toPageQuizResponse(
                        quizzes,
                        enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, getQuizRequest.getCategory())
                )
        );
    }

    @GetMapping("/quizzes/search")
    public ResponseEntity<GetPagingQuizResponse> searchQuizList(@Valid final SearchQuizListRequest request) {
        final PagingQuizzes quizzes = quizService.searchQuizList(request);
        return ResponseEntity.ok(toPageQuizResponse(quizzes, null));
    }

    private GetPagingQuizResponse toPageQuizResponse(final PagingQuizzes quizzes, final EnumMapperValue category) {
        return GetPagingQuizResponse.builder()
                .quizList(quizzes.getQuizzes().getQuizList().stream().map(QuizDtoConverter::convert).collect(Collectors.toList()))
                .category(category)
                .hasNext(quizzes.isHasNext())
                .page(quizzes.getPageNumber())
                .size(quizzes.getPageSize())
                .totalPages(quizzes.getTotalPages())
                .build();
    }

}