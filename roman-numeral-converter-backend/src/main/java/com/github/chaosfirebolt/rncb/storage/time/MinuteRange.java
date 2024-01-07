package com.github.chaosfirebolt.rncb.storage.time;

import com.github.chaosfirebolt.rncb.limit.DefaultRequestLimit;
import com.github.chaosfirebolt.rncb.limit.RequestLimitFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class MinuteRange extends BaseTimeRange {

  public MinuteRange(Instant max) {
    super(max.minus(1, ChronoUnit.MINUTES), max);
  }

  @Override
  public RequestLimitFactory requestLimitFactory() {
    return (requestCount, limitation) -> new DefaultRequestLimit(requestCount, limitation.perMinute());
  }
}
