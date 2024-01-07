package com.github.chaosfirebolt.rncb.limit;

public record DefaultRequestLimit(int requestCount, int limit) implements RequestLimit {

  @Override
  public boolean isReached() {
    return requestCount >= limit;
  }
}
