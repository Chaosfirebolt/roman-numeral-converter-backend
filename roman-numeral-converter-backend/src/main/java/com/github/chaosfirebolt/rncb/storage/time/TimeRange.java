package com.github.chaosfirebolt.rncb.storage.time;

import java.time.Instant;

public interface TimeRange {

  Instant min();
  Instant max();
}
