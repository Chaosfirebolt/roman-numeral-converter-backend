package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Clock;
import java.util.List;

public class RegistrationThrottlingFilter extends ThrottlingFilter {

  private static final String DEFAULT_IDENTIFICATION = "UNKNOWN";

  public RegistrationThrottlingFilter(Clock appClock, RequestStorage requestStorage, List<TimeRangeFactory> factories) {
    super(appClock, requestStorage, factories);
  }

  @Override
  protected String identifyClient(HttpServletRequest request) {
    //consider, maybe just limit all?
    String ip = request.getRemoteAddr();
    if (ip == null) {
      return DEFAULT_IDENTIFICATION;
    }
    return ip;
  }
}
