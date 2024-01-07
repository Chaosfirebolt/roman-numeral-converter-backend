package com.github.chaosfirebolt.rncb.app;

import com.github.chaosfirebolt.rncb.limit.ApplicationRateLimit;
import com.github.chaosfirebolt.rncb.persist.BaseEntity;
import com.github.chaosfirebolt.rncb.request.ApplicationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "applications")
public class Application extends BaseEntity<Long> {

  @Column(name = "app_id", unique = true, nullable = false, updatable = false)
  private String applicationId;
  @Column(name = "app_pass", nullable = false, updatable = false)
  private String applicationPassword;
  @OneToOne
  @JoinColumn(name = "rate_limit_id", referencedColumnName = "id", nullable = false)
  private ApplicationRateLimit rateLimit;
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

  public ApplicationRateLimit getRateLimit() {
    return rateLimit;
  }

  public void setRateLimit(ApplicationRateLimit rateLimit) {
    this.rateLimit = rateLimit;
  }

  public List<ApplicationRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<ApplicationRequest> requests) {
    this.requests = requests;
  }
}
