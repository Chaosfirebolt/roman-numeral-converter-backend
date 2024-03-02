package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.chaosfirebolt.converter.RomanInteger;
import org.checkerframework.checker.index.qual.NonNegative;

import java.time.Duration;
import java.util.Set;

public class CaffeineExpiry implements Expiry<String, RomanInteger> {

  private final Set<RomanInteger> expiryExcludes;
  private final Ticker ticker;
  private final long expiryNanos;

  private CaffeineExpiry(Set<RomanInteger> expiryExcludes, Ticker ticker, long expiryNanos) {
    this.expiryExcludes = expiryExcludes;
    this.ticker = ticker;
    this.expiryNanos = expiryNanos;
  }

  public CaffeineExpiry(Duration expiryDuration, Ticker ticker, RomanInteger... expiryExcludes) {
    this(Set.of(expiryExcludes), ticker, expiryDuration.toNanos());
  }

  @Override
  public long expireAfterCreate(String key, RomanInteger value, long currentTime) {
    return Long.MAX_VALUE;
  }

  @Override
  public long expireAfterUpdate(String key, RomanInteger value, long currentTime, @NonNegative long currentDuration) {
    return Long.MAX_VALUE;
  }

  @Override
  public long expireAfterRead(String key, RomanInteger value, long currentTime, @NonNegative long currentDuration) {
    if (expiryExcludes.contains(value)) {
      return Long.MAX_VALUE;
    }
    //TODO finish
    return 0;
  }
}
