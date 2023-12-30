package com.github.chaosfirebolt.rncb.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "applications")
public class Application extends AbstractPersistable<Long> {

  @Column(name = "app_id", unique = true, nullable = false, updatable = false)
  private String applicationId;
  @Column(name = "app_pass", unique = true, nullable = false, updatable = false)
  private String applicationPassword;
  @Column(name = "limit_per_minute", nullable = false)
  private Integer limitPerMinute;
  @Column(name = "limit_per_hour", nullable = false)
  private Integer limitPerHour;

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public String getApplicationPassword() {
    return applicationPassword;
  }

  public void setApplicationPassword(String applicationPassword) {
    this.applicationPassword = applicationPassword;
  }

  public Integer getLimitPerMinute() {
    return limitPerMinute;
  }

  public void setLimitPerMinute(Integer limitPerMinute) {
    this.limitPerMinute = limitPerMinute;
  }

  public Integer getLimitPerHour() {
    return limitPerHour;
  }

  public void setLimitPerHour(Integer limitPerHour) {
    this.limitPerHour = limitPerHour;
  }
}
