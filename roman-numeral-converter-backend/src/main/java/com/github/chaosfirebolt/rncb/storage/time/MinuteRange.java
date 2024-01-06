package com.github.chaosfirebolt.rncb.storage.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class MinuteRange extends BaseTimeRange {

  public MinuteRange(Instant max) {
    super(max.minus(1, ChronoUnit.MINUTES), max);
  }
}
