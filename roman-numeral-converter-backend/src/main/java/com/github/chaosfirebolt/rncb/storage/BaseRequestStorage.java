package com.github.chaosfirebolt.rncb.storage;

import java.time.Clock;
import java.time.Instant;

abstract class BaseRequestStorage implements RequestStorage {

  private final Clock clock;

  BaseRequestStorage(Clock clock) {
    this.clock = clock;
  }

  @Override
  public void store(String identifier) {
    Instant requestTime = Instant.now(clock);
    store(identifier, requestTime);
  }

  protected abstract void store(String identifier, Instant requestTime);
}
