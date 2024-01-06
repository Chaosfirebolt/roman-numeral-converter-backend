package com.github.chaosfirebolt.rncb.storage.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HourRange extends BaseTimeRange {

  public HourRange(Instant max) {
    super(max.minus(1, ChronoUnit.HOURS), max);
  }
}
