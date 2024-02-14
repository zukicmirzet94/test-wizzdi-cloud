package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Organization */
@IdValid.List({
  @IdValid(
      targetField = "organization",
      field = "id",
      fieldType = Organization.class,
      groups = {Update.class})
})
public class OrganizationUpdate extends OrganizationCreate {

  private String id;

  @JsonIgnore private Organization organization;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return OrganizationUpdate
   */
  public <T extends OrganizationUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return organization
   */
  @JsonIgnore
  public Organization getOrganization() {
    return this.organization;
  }

  /**
   * @param organization organization to set
   * @return OrganizationUpdate
   */
  public <T extends OrganizationUpdate> T setOrganization(Organization organization) {
    this.organization = organization;
    return (T) this;
  }
}
