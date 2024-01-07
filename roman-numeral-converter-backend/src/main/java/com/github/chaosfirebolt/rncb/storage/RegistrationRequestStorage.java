package com.github.chaosfirebolt.rncb.storage;

import com.github.chaosfirebolt.rncb.limit.Limitation;
import com.github.chaosfirebolt.rncb.limit.RequestLimit;
import com.github.chaosfirebolt.rncb.storage.time.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("reg-storage")
public class RegistrationRequestStorage extends BaseRequestStorage {

  private static final Duration RETENTION_TIME = Duration.ofHours(1);
  private static final Limitation REGISTRATION_LIMITATION = new RegistrationLimitation(1, 10);

  private final Map<String, List<Instant>> storage;

  @Autowired
  public RegistrationRequestStorage(Clock clock) {
    super(clock);
    this.storage = Collections.synchronizedMap(new HashMap<>());
  }

  @Override
  protected void store(String identifier, Instant requestTime) {
    getRequestTimes(identifier).add(requestTime);
  }

  private List<Instant> getRequestTimes(String identifier) {
    return storage.computeIfAbsent(identifier, k -> Collections.synchronizedList(new ArrayList<>()));
  }

  @Override
  public RequestLimit extract(String identifier, TimeRange timeRange) {
    int requestCount = (int) getRequestTimes(identifier)
            .stream()
            .filter(reqTime -> reqTime.compareTo(timeRange.min()) >= 0)
            .filter(reqTime -> reqTime.compareTo(timeRange.max()) <= 0)
            .count();
    return timeRange.requestLimitFactory().create(requestCount, REGISTRATION_LIMITATION);
  }
  @Scheduled(initialDelay = 1, fixedRate = 1, timeUnit = TimeUnit.HOURS)
  public void cleanUpStore() {
    synchronized (storage) {
      Iterator<Map.Entry<String, List<Instant>>> iterator = storage.entrySet().iterator();
      Instant now = Instant.now(clock);
      while (iterator.hasNext()) {
        Map.Entry<String, List<Instant>> entry = iterator.next();
        List<Instant> requestTimes = entry.getValue();
        requestTimes.removeIf(time -> Duration.between(time, now).compareTo(RETENTION_TIME) >= 0);
        if (requestTimes.isEmpty()) {
          iterator.remove();
        }
      }
    }
  }

  private record RegistrationLimitation(int limitPerMinute, int limitPerHour) implements Limitation {

    @Override
    public int perMinute() {
      return limitPerMinute;
    }

    @Override
    public int perHour() {
      return limitPerHour;
    }
  }
}
