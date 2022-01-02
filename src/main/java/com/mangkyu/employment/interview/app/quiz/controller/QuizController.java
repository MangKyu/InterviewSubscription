package com.mangkyu.employment.interview.app.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.dto.*;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

import static com.mangkyu.employment.interview.app.quiz.constants.QuizConstants.*;

@RestController
@RequiredArgsConstructor
public class QuizController {

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

    @GetMapping("/quiz/{id}")
    public ResponseEntity<GetQuizResponse> getQuiz(@PathVariable final long id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @Valid
    @GetMapping("/quizzes")
    public ResponseEntity<GetQuizResponseHolder> getQuizList(
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Range(min = MIN_PAGE_SIZE, max = MAX_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) @Range(min = MIN_PAGE_NUMBER) int page,
            @NotNull final QuizCategory category) {

        final GetQuizResponseHolder response = quizService.getQuizList(QuizDtoConverter.convert(size, page, category));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/categories")
    public ResponseEntity<QuizCategoryResponseHolder> getQuizCategoryList() {
        final QuizCategoryResponseHolder response = QuizCategoryResponseHolder.builder()
                .categoryList(quizService.getQuizCategoryList())
                .build();
        return ResponseEntity.ok(response);
    }

}