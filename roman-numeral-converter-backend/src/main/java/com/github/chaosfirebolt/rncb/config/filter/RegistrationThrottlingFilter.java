package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Clock;
import java.util.List;

public class RegistrationThrottlingFilter extends ThrottlingFilter {

  public RegistrationThrottlingFilter(Clock appClock, RequestStorage requestStorage, List<TimeRangeFactory> factories) {
    super(appClock, requestStorage, factories);
  }

  @Override
  protected String identifyClient(HttpServletRequest request) {
    return null;
  }
}
