package com.github.chaosfirebolt.rncb.storage.time;

import com.github.chaosfirebolt.rncb.request.limit.RequestLimitFactory;

import java.time.Instant;

public interface TimeRange {

  Instant min();
  Instant max();
  RequestLimitFactory requestLimitFactory();
}
