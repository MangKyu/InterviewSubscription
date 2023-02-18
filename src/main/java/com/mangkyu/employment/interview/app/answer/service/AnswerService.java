package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.controller.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.controller.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final QuizService quizService;
    private final AnswerRepository answerRepository;

    public GetAnswerResponse getAnswer(final String resourceId) throws RestApiException {
        final Answer answer = answerRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        return QuizDtoConverter.convert(answer);
    }

    @Transactional
    public void addAnswer(final AddAnswerRequest addAnswerRequest) throws RestApiException {
        final Quiz quiz = quizService.findQuiz(addAnswerRequest.getQuizResourceId());
        final Answer quizAnswer = quiz.getAnswer();
        if (quizAnswer == null) {
            final Answer answer = QuizDtoConverter.convert(addAnswerRequest, quiz);
            answerRepository.save(answer);
            quiz.setAnswer(answer);
        } else {
            quizAnswer.setDescription(addAnswerRequest.getDescription());
        }
    }

    @Transactional
    public void deleteAnswer(final String resourceId) throws RestApiException {
        final Answer answer = answerRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
        answer.getQuiz().setAnswer(null);
        answerRepository.delete(answer);
    }
}