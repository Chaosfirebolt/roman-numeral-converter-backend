package com.github.chaosfirebolt.rncb.app;

import com.github.chaosfirebolt.rncb.persist.BaseEntity;
import com.github.chaosfirebolt.rncb.request.ApplicationRequest;
import com.github.chaosfirebolt.rncb.request.limit.Limitation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "applications")
public class Application extends BaseEntity<Long> implements Limitation {

  @Column(name = "app_id", unique = true, nullable = false, updatable = false)
  private String applicationId;
  @Column(name = "app_pass", unique = true, nullable = false, updatable = false)
  private String applicationPassword;
  @Column(name = "limit_per_minute", nullable = false)
  private int limitPerMinute;
  @Column(name = "limit_per_hour", nullable = false)
  private int limitPerHour;
  @OneToMany(mappedBy = "madeBy")
  private List<ApplicationRequest> requests;

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

  public int getLimitPerMinute() {
    return limitPerMinute;
  }

  public void setLimitPerMinute(int limitPerMinute) {
    this.limitPerMinute = limitPerMinute;
  }

  public int getLimitPerHour() {
    return limitPerHour;
  }

  public void setLimitPerHour(int limitPerHour) {
    this.limitPerHour = limitPerHour;
  }

  public List<ApplicationRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<ApplicationRequest> requests) {
    this.requests = requests;
  }

  @Override
  public int perMinute() {
    return getLimitPerMinute();
  }

  @Override
  public int perHour() {
    return getLimitPerHour();
  }
}
