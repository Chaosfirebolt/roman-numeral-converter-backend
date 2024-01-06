package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Clock;
import java.util.List;

public class ConversionThrottlingFilter extends ThrottlingFilter {
  
  public ConversionThrottlingFilter(Clock appClock, RequestStorage requestStorage, List<TimeRangeFactory> factories) {
    super(appClock, requestStorage, factories);
  }

  @Override
  protected String identifyClient(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userDetails.getUsername();
    }
    //should never happen, user is authenticated at this point
    return null;
  }
}
