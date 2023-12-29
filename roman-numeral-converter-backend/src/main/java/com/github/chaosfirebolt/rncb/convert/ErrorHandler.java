package com.github.chaosfirebolt.rncb.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  @ExceptionHandler(ConversionException.class)
  public ResponseEntity<ProblemDetail> invalidFormatHandler(ConversionException exc) {
    String errorMessage = "Unable to convert input to roman numeral";
    logger.error("{} - {}", errorMessage, exc.getMessage());
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
    return ResponseEntity.of(problemDetail).build();
  }
}
