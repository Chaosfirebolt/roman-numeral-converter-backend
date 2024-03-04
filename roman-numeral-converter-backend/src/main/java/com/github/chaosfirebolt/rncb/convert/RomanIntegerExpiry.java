package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Expiry;
import com.github.chaosfirebolt.converter.RomanInteger;
import org.checkerframework.checker.index.qual.NonNegative;

import java.time.Duration;
import java.util.Set;

public class RomanIntegerExpiry implements Expiry<String, RomanInteger> {

  private final Set<RomanInteger> expiryExcludes;
  private final long expiryNanos;

  private RomanIntegerExpiry(Set<RomanInteger> expiryExcludes, long expiryNanos) {
    this.expiryExcludes = expiryExcludes;
    this.expiryNanos = expiryNanos;
  }

  public RomanIntegerExpiry(Duration expiryDuration, RomanInteger... expiryExcludes) {
    this(Set.of(expiryExcludes), expiryDuration.toNanos());
  }

  @Override
  public long expireAfterCreate(String key, RomanInteger value, long currentTime) {
    return calculateExpiry(value);
  }

  private long calculateExpiry(RomanInteger value) {
    if (expiryExcludes.contains(value)) {
      return Long.MAX_VALUE;
    }
    return expiryNanos;
  }

  @Override
  public long expireAfterUpdate(String key, RomanInteger value, long currentTime, @NonNegative long currentDuration) {
    return Long.MAX_VALUE;
  }

  @Override
  public long expireAfterRead(String key, RomanInteger value, long currentTime, @NonNegative long currentDuration) {
    return calculateExpiry(value);
  }
}
