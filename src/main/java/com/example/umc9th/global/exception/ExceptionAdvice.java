package com.example.umc9th.global.exception;

import com.example.umc9th.global.common.ApiResponse;
import com.example.umc9th.global.common.ReasonDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException e, HttpServletRequest request) {
        ReasonDTO errorReasonHttpStatus = e.getErrorReasonHttpStatus();
        ApiResponse<Object> response = ApiResponse.onFailure(errorReasonHttpStatus.getCode(), errorReasonHttpStatus.getMessage(), null);
        return new ResponseEntity<>(response, errorReasonHttpStatus.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception: ", e); // 로깅

        ErrorStatus errorStatus = ErrorStatus._INTERNAL_SERVER_ERROR;
        ApiResponse<Object> response = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);
        return new ResponseEntity<>(response, errorStatus.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
            errors.put(fieldName, errorMessage);
        });

        ErrorStatus errorStatus = ErrorStatus._BAD_REQUEST;
        ApiResponse<Object> response = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), errors);

        return new ResponseEntity<>(response, errorStatus.getHttpStatus());
    }
}