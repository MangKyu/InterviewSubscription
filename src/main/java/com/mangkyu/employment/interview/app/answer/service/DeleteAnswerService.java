package com.mangkyu.employment.interview.app.answer.service;

import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.answer.repository.AnswerRepository;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteAnswerService {

    private final GetAnswerService getAnswerService;
    private final AnswerRepository answerRepository;

    public void delete(final String resourceId) {
        final Answer answer = getAnswerService.get(resourceId);
        answer.clearAnswer();
        answerRepository.delete(answer);
    }
}