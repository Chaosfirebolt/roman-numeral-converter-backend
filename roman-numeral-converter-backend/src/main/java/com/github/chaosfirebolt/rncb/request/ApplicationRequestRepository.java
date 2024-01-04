package com.github.chaosfirebolt.rncb.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {

  @Query("""
          SELECT ar
            FROM ApplicationRequest AS ar
            WHERE ar.madeBy = :applicationId
            AND ar.madeAt BETWEEN :start AND :end
          """)
  List<ApplicationRequest> findByAppIdAndMadeAtRange(Long applicationId, Instant start, Instant end);
}
