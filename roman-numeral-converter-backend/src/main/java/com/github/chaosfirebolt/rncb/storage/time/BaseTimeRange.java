package com.github.chaosfirebolt.rncb.storage.time;

import java.time.Instant;

public abstract class BaseTimeRange implements TimeRange {

  private final Instant min;
  private final Instant max;

  protected BaseTimeRange(Instant min, Instant max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public Instant min() {
    return min;
  }

  @Override
  public Instant max() {
    return max;
  }
}
