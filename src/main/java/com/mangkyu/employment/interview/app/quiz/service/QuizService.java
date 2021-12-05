package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;

    public void addQuiz(final AddQuizRequest addQuizRequest) {
        final QuizEntity quizEntity = modelMapper.map(addQuizRequest, QuizEntity.class);
        System.out.println(quizEntity);
        quizRepository.save(quizEntity);
    }

}
