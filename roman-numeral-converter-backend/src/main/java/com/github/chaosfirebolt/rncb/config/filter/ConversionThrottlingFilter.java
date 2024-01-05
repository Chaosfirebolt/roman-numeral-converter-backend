package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Clock;

public class ConversionThrottlingFilter extends ThrottlingFilter {
  
  public ConversionThrottlingFilter(Clock appClock, RequestStorage requestStorage) {
    super(appClock, requestStorage);
  }

  @Override
  protected String identifyClient(HttpServletRequest request) {
    return null;
  }
}
