package com.github.chaosfirebolt.rncb.storage;

import java.time.Instant;

public record TimeRange(Instant min, Instant max) {
}
