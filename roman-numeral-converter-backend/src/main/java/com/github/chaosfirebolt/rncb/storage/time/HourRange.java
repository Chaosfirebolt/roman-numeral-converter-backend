package com.github.chaosfirebolt.rncb.storage.time;

import com.github.chaosfirebolt.rncb.limit.DefaultRequestLimit;
import com.github.chaosfirebolt.rncb.limit.RequestLimitFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class HourRange extends BaseTimeRange {

  public HourRange(Instant max) {
    super(max.minus(1, ChronoUnit.HOURS), max);
  }

  @Override
  public RequestLimitFactory requestLimitFactory() {
    return (requestCount, limitation) -> new DefaultRequestLimit(requestCount, limitation.perHour());
  }
}
