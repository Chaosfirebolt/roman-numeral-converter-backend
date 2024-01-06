package com.github.chaosfirebolt.rncb.storage;

import com.github.chaosfirebolt.rncb.app.Application;
import com.github.chaosfirebolt.rncb.app.ApplicationRepository;
import com.github.chaosfirebolt.rncb.request.ApplicationRequest;
import com.github.chaosfirebolt.rncb.request.ApplicationRequestRepository;
import com.github.chaosfirebolt.rncb.request.limit.RequestLimit;
import com.github.chaosfirebolt.rncb.storage.time.TimeRange;

import java.time.Clock;
import java.time.Instant;

public class ApplicationRequestStorage extends BaseRequestStorage {

  private final ApplicationRepository applicationRepository;
  private final ApplicationRequestRepository requestRepository;

  public ApplicationRequestStorage(Clock clock, ApplicationRepository applicationRepository, ApplicationRequestRepository requestRepository) {
    super(clock);
    this.applicationRepository = applicationRepository;
    this.requestRepository = requestRepository;
  }

  @Override
  protected void store(String identifier, Instant requestTime) {
    Application application = applicationRepository.findByApplicationId(identifier).orElseThrow();
    ApplicationRequest request = new ApplicationRequest();
    request.setMadeAt(requestTime);
    request.setMadeBy(application);
    requestRepository.save(request);
  }

  @Override
  public RequestLimit extract(String identifier, TimeRange timeRange) {
    Application application = applicationRepository.findByApplicationId(identifier).orElseThrow();
    int requestCount = requestRepository.findRequestCountByAppIdAndTimeRange(application.getId(), timeRange.min(), timeRange.max());
    return timeRange.requestLimitFactory().create(requestCount, application);
  }
}
