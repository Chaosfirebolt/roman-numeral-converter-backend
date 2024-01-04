package com.github.chaosfirebolt.rncb.request;

import com.github.chaosfirebolt.rncb.app.Application;
import com.github.chaosfirebolt.rncb.persist.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "application_requests")
//TODO add index for made_at!
public class ApplicationRequest extends BaseEntity<Long> {

  @Column(name = "made_at", nullable = false, updatable = false)
  private Instant madeAt;
  @ManyToOne(optional = false)
  @JoinColumn(name = "application_id", nullable = false, updatable = false)
  private Application madeBy;

  public Instant getMadeAt() {
    return madeAt;
  }

  public void setMadeAt(Instant madeAt) {
    this.madeAt = madeAt;
  }

  public Application getMadeBy() {
    return madeBy;
  }

  public void setMadeBy(Application madeBy) {
    this.madeBy = madeBy;
  }
}
