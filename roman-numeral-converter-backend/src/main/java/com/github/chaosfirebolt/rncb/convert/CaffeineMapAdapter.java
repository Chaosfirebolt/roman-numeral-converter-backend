package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.chaosfirebolt.converter.RomanInteger;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class CaffeineMapAdapter implements Map<String, RomanInteger> {

  private final Cache<String, RomanInteger> cache;

  CaffeineMapAdapter(Cache<String, RomanInteger> cache) {
    this.cache = cache;
  }

  @Override
  public int size() {
    return (int) cache.estimatedSize();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    return getValue(key) != null;
  }

  private RomanInteger getValue(Object key) {
    if (key instanceof String stringKey) {
      return cache.getIfPresent(stringKey);
    }
    return null;
  }

  @Override
  public boolean containsValue(Object value) {
    return cache.asMap().containsValue(value);
  }

  @Override
  public RomanInteger get(Object key) {
    return getValue(key);
  }

  @Override
  public RomanInteger put(String key, RomanInteger value) {
    return cache.asMap().put(key, value);
  }

  @Override
  public RomanInteger remove(Object key) {
    return cache.asMap().remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ? extends RomanInteger> m) {
    cache.putAll(m);
  }

  @Override
  public void clear() {
    cache.invalidateAll();
  }

  @Override
  public Set<String> keySet() {
    return cache.asMap().keySet();
  }

  @Override
  public Collection<RomanInteger> values() {
    return cache.asMap().values();
  }

  @Override
  public Set<Entry<String, RomanInteger>> entrySet() {
    return cache.asMap().entrySet();
  }

  @Override
  public RomanInteger computeIfAbsent(String key, Function<? super String, ? extends RomanInteger> mappingFunction) {
    return cache.get(key, mappingFunction);
  }
}
