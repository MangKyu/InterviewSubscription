package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.controller.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddQuizService {

    private final QuizRepository quizRepository;
    @Deprecated // TODO: remove modelMapper
    private final ModelMapper modelMapper;

    public void addQuiz(final AddQuizRequest addQuizRequest) {
        final Quiz quiz = modelMapper.map(addQuizRequest, Quiz.class);
        quizRepository.save(quiz);
    }

}
