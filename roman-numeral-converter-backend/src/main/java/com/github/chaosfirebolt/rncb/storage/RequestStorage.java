package com.github.chaosfirebolt.rncb.storage;

import com.github.chaosfirebolt.rncb.request.limit.RequestLimit;
import com.github.chaosfirebolt.rncb.storage.time.TimeRange;

public interface RequestStorage {

  void store(String identifier);
  RequestLimit extract(String identifier, TimeRange timeRange);
}
