package com.mirzet.zukic.runtime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class Basic {

  @Id private String id;

  private String name;

  private String description;

  private OffsetDateTime updateDate;

  private OffsetDateTime creationDate;

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return Basic
   */
  public <T extends Basic> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name name to set
   * @return Basic
   */
  public <T extends Basic> T setName(String name) {
    this.name = name;
    return (T) this;
  }

  /**
   * @return description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @param description description to set
   * @return Basic
   */
  public <T extends Basic> T setDescription(String description) {
    this.description = description;
    return (T) this;
  }

  /**
   * @return updateDate
   */
  public OffsetDateTime getUpdateDate() {
    return this.updateDate;
  }

  /**
   * @param updateDate updateDate to set
   * @return Basic
   */
  public <T extends Basic> T setUpdateDate(OffsetDateTime updateDate) {
    this.updateDate = updateDate;
    return (T) this;
  }

  /**
   * @return creationDate
   */
  public OffsetDateTime getCreationDate() {
    return this.creationDate;
  }

  /**
   * @param creationDate creationDate to set
   * @return Basic
   */
  public <T extends Basic> T setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return (T) this;
  }
}
