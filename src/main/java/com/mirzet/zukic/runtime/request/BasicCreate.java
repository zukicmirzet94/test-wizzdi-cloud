package com.mirzet.zukic.runtime.request;

import java.time.OffsetDateTime;

/** Object Used to Create Basic */
public class BasicCreate {

  private OffsetDateTime creationDate;

  private String description;

  private String name;

  private OffsetDateTime updateDate;

  /**
   * @return creationDate
   */
  public OffsetDateTime getCreationDate() {
    return this.creationDate;
  }

  /**
   * @param creationDate creationDate to set
   * @return BasicCreate
   */
  public <T extends BasicCreate> T setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
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
   * @return BasicCreate
   */
  public <T extends BasicCreate> T setDescription(String description) {
    this.description = description;
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
   * @return BasicCreate
   */
  public <T extends BasicCreate> T setName(String name) {
    this.name = name;
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
   * @return BasicCreate
   */
  public <T extends BasicCreate> T setUpdateDate(OffsetDateTime updateDate) {
    this.updateDate = updateDate;
    return (T) this;
  }
}
