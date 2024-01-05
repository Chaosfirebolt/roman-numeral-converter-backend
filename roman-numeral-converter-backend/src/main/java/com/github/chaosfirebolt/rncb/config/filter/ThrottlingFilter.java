package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.request.RequestLimit;
import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.TimeRange;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

abstract class ThrottlingFilter extends OncePerRequestFilter {

  private final Clock clock;
  private final RequestStorage requestStorage;
  //TODO change time range to interface
  //TODO list of time range factories

  protected ThrottlingFilter(Clock clock, RequestStorage requestStorage) {
    this.clock = clock;
    this.requestStorage = requestStorage;
  }

  @Override
  protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Instant now = Instant.now(clock);
    String identifier = identifyClient(request);

    RequestLimit perMinuteLimit = requestStorage.extract(identifier, new TimeRange(now.minus(1, ChronoUnit.MINUTES), now));
    if (perMinuteLimit.isReached()) {
      setTooManyRequestResponse(response);
      return;
    }

    RequestLimit perHourLimit = requestStorage.extract(identifier, new TimeRange(now.minus(1, ChronoUnit.HOURS), now));
    if (perHourLimit.isReached()) {
      setTooManyRequestResponse(response);
      return;
    }

    requestStorage.store(identifier);
    filterChain.doFilter(request, response);
  }

  protected abstract String identifyClient(HttpServletRequest request);

  private void setTooManyRequestResponse(HttpServletResponse response) throws IOException {
    HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
    response.sendError(tooManyRequests.value(), tooManyRequests.getReasonPhrase());
  }
}
