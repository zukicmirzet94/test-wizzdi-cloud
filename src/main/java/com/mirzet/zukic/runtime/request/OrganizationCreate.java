package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create Organization */
@IdValid.List({
  @IdValid(
      targetField = "organizationType",
      field = "organizationTypeId",
      fieldType = OrganizationType.class,
      groups = {Update.class, Create.class})
})
public class OrganizationCreate extends BasicCreate {

  private String country;

  @JsonIgnore private OrganizationType organizationType;

  private String organizationTypeId;

  private String type;

  /**
   * @return country
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * @param country country to set
   * @return OrganizationCreate
   */
  public <T extends OrganizationCreate> T setCountry(String country) {
    this.country = country;
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
   * @return OrganizationCreate
   */
  public <T extends OrganizationCreate> T setOrganizationType(OrganizationType organizationType) {
    this.organizationType = organizationType;
    return (T) this;
  }

  /**
   * @return organizationTypeId
   */
  public String getOrganizationTypeId() {
    return this.organizationTypeId;
  }

  /**
   * @param organizationTypeId organizationTypeId to set
   * @return OrganizationCreate
   */
  public <T extends OrganizationCreate> T setOrganizationTypeId(String organizationTypeId) {
    this.organizationTypeId = organizationTypeId;
    return (T) this;
  }

  /**
   * @return type
   */
  public String getType() {
    return this.type;
  }

  /**
   * @param type type to set
   * @return OrganizationCreate
   */
  public <T extends OrganizationCreate> T setType(String type) {
    this.type = type;
    return (T) this;
  }
}
