package com.github.chaosfirebolt.rncb.io;

public record ErrorResponse(String message, Object details) {

  public static ErrorResponse create(String message) {
    return new ErrorResponse(message, null);
  }
}
