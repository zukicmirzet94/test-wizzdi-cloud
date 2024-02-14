package com.mirzet.zukic.runtime.request;

import jakarta.validation.constraints.Min;
import java.time.OffsetDateTime;
import java.util.Set;

/** Object Used to List Basic */
public class BasicFilter {

  private OffsetDateTime creationDateEnd;

  private OffsetDateTime creationDateStart;

  @Min(value = 0)
  private Integer currentPage;

  private Set<String> description;

  private Set<String> id;

  private Set<String> name;

  @Min(value = 1)
  private Integer pageSize;

  private OffsetDateTime updateDateEnd;

  private OffsetDateTime updateDateStart;

  /**
   * @return creationDateEnd
   */
  public OffsetDateTime getCreationDateEnd() {
    return this.creationDateEnd;
  }

  /**
   * @param creationDateEnd creationDateEnd to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setCreationDateEnd(OffsetDateTime creationDateEnd) {
    this.creationDateEnd = creationDateEnd;
    return (T) this;
  }

  /**
   * @return creationDateStart
   */
  public OffsetDateTime getCreationDateStart() {
    return this.creationDateStart;
  }

  /**
   * @param creationDateStart creationDateStart to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setCreationDateStart(OffsetDateTime creationDateStart) {
    this.creationDateStart = creationDateStart;
    return (T) this;
  }

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage currentPage to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return description
   */
  public Set<String> getDescription() {
    return this.description;
  }

  /**
   * @param description description to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setDescription(Set<String> description) {
    this.description = description;
    return (T) this;
  }

  /**
   * @return id
   */
  public Set<String> getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return name
   */
  public Set<String> getName() {
    return this.name;
  }

  /**
   * @param name name to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setName(Set<String> name) {
    this.name = name;
    return (T) this;
  }

  /**
   * @return pageSize
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param pageSize pageSize to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }

  /**
   * @return updateDateEnd
   */
  public OffsetDateTime getUpdateDateEnd() {
    return this.updateDateEnd;
  }

  /**
   * @param updateDateEnd updateDateEnd to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setUpdateDateEnd(OffsetDateTime updateDateEnd) {
    this.updateDateEnd = updateDateEnd;
    return (T) this;
  }

  /**
   * @return updateDateStart
   */
  public OffsetDateTime getUpdateDateStart() {
    return this.updateDateStart;
  }

  /**
   * @param updateDateStart updateDateStart to set
   * @return BasicFilter
   */
  public <T extends BasicFilter> T setUpdateDateStart(OffsetDateTime updateDateStart) {
    this.updateDateStart = updateDateStart;
    return (T) this;
  }
}
