package com.mangkyu.employment.interview.app.common.erros.exception;


import com.mangkyu.employment.interview.app.common.erros.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizException extends Exception {

    private final ErrorCode errorCode;

}
