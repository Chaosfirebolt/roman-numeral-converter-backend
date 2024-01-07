package com.github.chaosfirebolt.rncb.limit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRateLimitRepository extends JpaRepository<ApplicationRateLimit, Long> {
}
