package com.mangkyu.employment.interview.app.common.erros.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();

    HttpStatus getHttpStatus();

    String getMessage();

}
