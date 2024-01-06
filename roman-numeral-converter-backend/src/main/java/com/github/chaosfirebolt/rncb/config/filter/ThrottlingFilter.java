package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.request.limit.RequestLimit;
import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.TimeRange;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

abstract class ThrottlingFilter extends OncePerRequestFilter {

  private final Clock clock;
  private final RequestStorage requestStorage;
  private final List<TimeRangeFactory> factories;

  protected ThrottlingFilter(Clock clock, RequestStorage requestStorage, List<TimeRangeFactory> factories) {
    this.clock = clock;
    this.requestStorage = requestStorage;
    this.factories = factories;
  }

  @Override
  protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Instant now = Instant.now(clock);
    String identifier = identifyClient(request);

    for (TimeRangeFactory factory : factories) {
      TimeRange testRange = factory.create(now);
      RequestLimit requestLimit = requestStorage.extract(identifier, testRange);
      if (requestLimit.isReached()) {
        HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
        response.sendError(tooManyRequests.value(), tooManyRequests.getReasonPhrase());
        return;
      }
    }

    requestStorage.store(identifier);
    filterChain.doFilter(request, response);
  }

  protected abstract String identifyClient(HttpServletRequest request);
}
