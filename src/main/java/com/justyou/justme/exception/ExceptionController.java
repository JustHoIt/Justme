package com.justyou.justme.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    /*기존 예외처리*/
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<?> customRequestException(final CustomException e) {
        log.warn("API Exception : {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler({ServletException.class})
    public ResponseEntity<?> servletException(final CustomException e) {
        log.warn("API Exception : {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) throws JsonProcessingException {
        /* Validation 예외처리 Log및 Swagger
        getBindingResult() 메서드를 호출하면 해당 요청의 유효성 검사 결과를 호출 후  BindingResult 객체로 반환 후
        Binding Result 의 0번째 메시지를 가져오도로 설정
        * */
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);

        String message = fieldError.getDefaultMessage();
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;

        log.warn("API Exception : {}", errorCode, message);
        return ResponseEntity.badRequest().body(new ExceptionResponse(message, errorCode));
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {
        private String message;
        private ErrorCode errorCode;
    }
}
