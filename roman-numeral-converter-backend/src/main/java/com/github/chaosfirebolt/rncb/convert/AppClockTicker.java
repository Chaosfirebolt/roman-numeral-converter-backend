package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Ticker;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

public class AppClockTicker implements Ticker {

  private final Clock applicationClock;

  public AppClockTicker(Clock applicationClock) {
    this.applicationClock = applicationClock;
  }

  @Override
  public long read() {
    long millis = applicationClock.instant().toEpochMilli();
    return TimeUnit.MILLISECONDS.toNanos(millis);
  }
}
