package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.common.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.app.common.erros.exception.QuizException;
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

    public GetAnswerResponse getAnswer(final String resourceId) throws QuizException {
        final Answer answer = answerRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new QuizException(CommonErrorCode.RESOURCE_NOT_FOUND));

        return QuizDtoConverter.convert(answer);
    }

    @Transactional
    public void addAnswer(final AddAnswerRequest addAnswerRequest) throws QuizException {
        final Quiz quiz = quizService.findQuiz(addAnswerRequest.getQuizResourceId());
        final Answer answer = QuizDtoConverter.convert(addAnswerRequest, quiz);
        answerRepository.save(answer);
        quiz.setAnswer(answer);
    }

}
