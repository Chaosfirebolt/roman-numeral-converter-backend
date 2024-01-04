package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;

import java.time.Clock;
import java.time.Instant;

public class RegistrationThrottlingFilter extends ThrottlingFilter {

  public RegistrationThrottlingFilter(Clock appClock, RequestStorage requestStorage) {
    super(appClock, requestStorage);
  }

  @Override
  protected boolean isMinuteLimitReached(Instant startTime, Instant endTime) {
    return false;
  }

  @Override
  protected boolean isHourLimitReached(Instant startTime, Instant endTime) {
    return false;
  }
}
