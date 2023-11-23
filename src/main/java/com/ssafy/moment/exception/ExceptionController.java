package com.ssafy.moment.exception;

import com.ssafy.moment.domain.dto.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
        CustomException.class
    })
    public ResponseDto<?> customRequestException
        (final CustomException c) {
        log.warn("api Exception : {}", c.getErrorCode());
        return ResponseDto.fail(new ExceptionResponse(c.getErrorCode(), c.getMessage()));
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {
        private ErrorCode errorCode;
        private String message;
    }
}
