package com.github.chaosfirebolt.rncb.convert;

import com.github.chaosfirebolt.rncb.io.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  @ExceptionHandler(ConversionException.class)
  public ResponseEntity<ErrorResponse> invalidFormatHandler(ConversionException exc) {
    String errorMessage = "Unable to convert input to roman numeral";
    logger.error("{} - {}", errorMessage, exc.getMessage());
    ErrorResponse response = ErrorResponse.create(errorMessage);
    return ResponseEntity.badRequest().body(response);
  }
}
