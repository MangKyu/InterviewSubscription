package com.mangkyu.employment.interview.app.category.controller;

import com.mangkyu.employment.interview.app.category.service.GetQuizCategoryService;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class GetQuizCategoryController {

    private final GetQuizCategoryService quizService;

    @GetMapping("/quizzes/categories")
    public ResponseEntity<Map<String, List<QuizCategoryResponse>>> getQuizCategoryList() {
        List<QuizCategoryResponse> categoryList = quizService.getQuizCategoryList()
                .stream()
                .map(this::convertToQuizCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("categoryList", categoryList));
    }

    private QuizCategoryResponse convertToQuizCategoryResponse(final EnumMapperValue enumMapperValue) {
        return QuizCategoryResponse.builder()
                .count(quizService.getCount(QuizCategory.valueOf(enumMapperValue.name())))
                .code(enumMapperValue.getCode())
                .title(enumMapperValue.getTitle())
                .desc(enumMapperValue.getDesc())
                .build();
    }

}