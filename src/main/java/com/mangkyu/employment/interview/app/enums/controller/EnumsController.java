package com.mangkyu.employment.interview.app.enums.controller;

import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnumsController {

    private final EnumMapperFactory enumMapperFactory;

    @GetMapping("/enums/quiz/category")
    public ResponseEntity<List<EnumMapperValue>> getQuizCategoryList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_CATEGORY));
    }

    @GetMapping("/enums/quiz/level")
    public ResponseEntity<List<EnumMapperValue>> getQuizLevelList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_LEVEL));
    }

    @GetMapping("/enums/quiz/day")
    public ResponseEntity<List<EnumMapperValue>> getQuizDayList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_DAY));
    }

}
