package com.github.chaosfirebolt.rncb.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {

  @Query("""
          SELECT COUNT(ar)
            FROM ApplicationRequest AS ar
            WHERE ar.madeBy.id = :appId
            AND ar.madeAt BETWEEN :start AND :end
          """)
  int findRequestCountByAppIdAndTimeRange(Long appId, Instant start, Instant end);
}
