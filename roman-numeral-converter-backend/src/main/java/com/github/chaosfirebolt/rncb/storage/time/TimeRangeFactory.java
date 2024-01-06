package com.github.chaosfirebolt.rncb.storage.time;

import java.time.Instant;

@FunctionalInterface
public interface TimeRangeFactory {

  TimeRange create(Instant now);
}
