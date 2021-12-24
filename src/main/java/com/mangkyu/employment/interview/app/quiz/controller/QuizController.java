package com.mangkyu.employment.interview.app.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequestHolder;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final EnumMapperFactory enumMapperFactory;
    private final QuizService quizService;

    @GetMapping("/quiz/init")
    public ResponseEntity<Void> initQuizListByFile() throws IOException {
        final File file = ResourceUtils.getFile("classpath:quiz/quiz.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final AddQuizRequestHolder addQuizRequestHolder = objectMapper.readValue(file, AddQuizRequestHolder.class);

        addQuizRequestHolder.getQuizList().forEach(quizService::addQuiz);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/quiz")
    public ResponseEntity<Void> addQuiz(@RequestBody @Valid final AddQuizRequest addQuizRequest) {
        quizService.addQuiz(addQuizRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/quiz/category")
    public ResponseEntity<List<EnumMapperValue>> getQuizCategoryList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_CATEGORY));
    }

    @GetMapping("/quiz/level")
    public ResponseEntity<List<EnumMapperValue>> getQuizLevelList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_LEVEL));
    }

    @GetMapping("/quiz/day")
    public ResponseEntity<List<EnumMapperValue>> getQuizDayList() {
        return ResponseEntity.ok(enumMapperFactory.get(EnumMapperKey.QUIZ_DAY));
    }

}
