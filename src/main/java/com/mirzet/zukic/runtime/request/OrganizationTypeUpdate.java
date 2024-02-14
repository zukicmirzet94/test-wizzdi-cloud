package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update OrganizationType */
@IdValid.List({
  @IdValid(
      targetField = "organizationType",
      field = "id",
      fieldType = OrganizationType.class,
      groups = {Update.class})
})
public class OrganizationTypeUpdate extends OrganizationTypeCreate {

  private String id;

  @JsonIgnore private OrganizationType organizationType;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return OrganizationTypeUpdate
   */
  public <T extends OrganizationTypeUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return organizationType
   */
  @JsonIgnore
  public OrganizationType getOrganizationType() {
    return this.organizationType;
  }

  /**
   * @param organizationType organizationType to set
   * @return OrganizationTypeUpdate
   */
  public <T extends OrganizationTypeUpdate> T setOrganizationType(
      OrganizationType organizationType) {
    this.organizationType = organizationType;
    return (T) this;
  }
}
