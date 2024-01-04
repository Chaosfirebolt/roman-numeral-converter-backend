package com.github.chaosfirebolt.rncb.storage;

import com.github.chaosfirebolt.rncb.request.RequestLimit;

public interface RequestStorage {

  void store(String identifier);
  RequestLimit extract(String identifier, TimeRange timeRange);
}
