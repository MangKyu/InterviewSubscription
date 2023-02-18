package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeleteAnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public void deleteAnswer(final String resourceId) throws RestApiException {
        final Answer answer = answerRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
        answer.getQuiz().setAnswer(null);
        answerRepository.delete(answer);
    }
}