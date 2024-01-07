package com.github.chaosfirebolt.rncb.limit;

import com.github.chaosfirebolt.rncb.app.Application;
import com.github.chaosfirebolt.rncb.persist.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_rate_limits")
public class ApplicationRateLimit extends BaseEntity<Long> implements Limitation {

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "rateLimit")
  private Application application;
  @Column(name = "limit_per_minute", nullable = false)
  private int limitPerMinute;
  @Column(name = "limit_per_hour", nullable = false)
  private int limitPerHour;

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
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

  @Override
  public int perMinute() {
    return limitPerMinute;
  }

  @Override
  public int perHour() {
    return limitPerHour;
  }
}
