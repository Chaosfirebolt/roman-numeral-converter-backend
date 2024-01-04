package com.github.chaosfirebolt.rncb.config.filter;

import com.github.chaosfirebolt.rncb.storage.RequestStorage;
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

//TODO save request times!
abstract class ThrottlingFilter extends OncePerRequestFilter {

  private final Clock clock;
  private final RequestStorage requestStorage;

  protected ThrottlingFilter(Clock clock, RequestStorage requestStorage) {
    this.clock = clock;
    this.requestStorage = requestStorage;
  }

  @Override
  protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Instant now = Instant.now(clock);
    //TODO check minute limit reached
    //TODO check hour limit reached
    //TODO store current request if checks pass
    filterChain.doFilter(request, response);
  }

  private void setTooManyRequestResponse(HttpServletResponse response) throws IOException {
    HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
    response.sendError(tooManyRequests.value(), tooManyRequests.getReasonPhrase());
  }
}
