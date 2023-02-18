package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.GetQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateAnswerService {

    private final GetQuizService quizService;

    public void add(final AddAnswerRequest request) {
        final Quiz quiz = quizService.getQuiz(request.getQuizResourceId());
        final Answer quizAnswer = quiz.getAnswer();
        if (quizAnswer != null) {
            quizAnswer.setDescription(request.getDescription());
        }
    }

}