package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;

import java.util.Optional;

class CaffeineStorageAdapter implements Storage<String, RomanInteger> {

  private final Cache<String, RomanInteger> caffeineCache;

  CaffeineStorageAdapter(Cache<String, RomanInteger> caffeineCache) {
    this.caffeineCache = caffeineCache;
  }

  @Override
  public void store(String key, RomanInteger value) {
    caffeineCache.asMap().putIfAbsent(key, value);
  }

  @Override
  public Optional<RomanInteger> retrieve(String key) {
    return Optional.ofNullable(caffeineCache.getIfPresent(key));
  }

  @Override
  public void remove(String key) {
    caffeineCache.invalidate(key);
  }

  @Override
  public void clear() {
    caffeineCache.invalidateAll();
  }

  @Override
  public RomanInteger compute(String key, Computation<String, RomanInteger> computation) {
    return caffeineCache.get(key, computation.unwrap());
  }
}
