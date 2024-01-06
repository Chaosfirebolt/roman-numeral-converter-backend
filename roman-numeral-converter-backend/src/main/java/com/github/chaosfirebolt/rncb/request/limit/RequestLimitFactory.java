package com.github.chaosfirebolt.rncb.request.limit;

@FunctionalInterface
public interface RequestLimitFactory {

  RequestLimit create(int requestCount, Limitation limitation);
}
