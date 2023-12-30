package com.github.chaosfirebolt.rncb.persist;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public class BaseEntity<K extends Number> implements Persistable<K> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private K id;

  @Override
  public K getId() {
    return id;
  }

  @Override
  @Transient
  public boolean isNew() {
    return id == null;
  }

  public void setId(K id) {
    this.id = id;
  }
}
