package com.github.chaosfirebolt.rncb.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  Optional<Application> findByApplicationId(String appId);
}
