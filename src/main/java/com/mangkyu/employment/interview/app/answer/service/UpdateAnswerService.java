package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateAnswerService {

    private final QuizService quizService;
    private final AnswerRepository answerRepository;

    public void add(final AddAnswerRequest request) {
        final Quiz quiz = quizService.findQuiz(request.getQuizResourceId());
        final Answer quizAnswer = quiz.getAnswer();
        if (quizAnswer == null) {
            final Answer answer = QuizDtoConverter.convert(request, quiz);
            answerRepository.save(answer);
            quiz.setAnswer(answer);
        } else {
            quizAnswer.setDescription(request.getDescription());
        }
    }

}