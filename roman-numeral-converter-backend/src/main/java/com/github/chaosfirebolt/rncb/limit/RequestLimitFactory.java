package com.github.chaosfirebolt.rncb.limit;

@FunctionalInterface
public interface RequestLimitFactory {

  RequestLimit create(int requestCount, Limitation limitation);
}
