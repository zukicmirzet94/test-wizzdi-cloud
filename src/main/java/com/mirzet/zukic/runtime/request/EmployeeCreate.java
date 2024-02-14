package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create Employee */
@IdValid.List({
  @IdValid(
      targetField = "qualification",
      field = "qualificationId",
      fieldType = Qualification.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "organization",
      field = "organizationId",
      fieldType = Organization.class,
      groups = {Update.class, Create.class})
})
public class EmployeeCreate extends PersonCreate {

  @JsonIgnore private Organization organization;

  private String organizationId;

  @JsonIgnore private Qualification qualification;

  private String qualificationId;

  /**
   * @return organization
   */
  @JsonIgnore
  public Organization getOrganization() {
    return this.organization;
  }

  /**
   * @param organization organization to set
   * @return EmployeeCreate
   */
  public <T extends EmployeeCreate> T setOrganization(Organization organization) {
    this.organization = organization;
    return (T) this;
  }

  /**
   * @return organizationId
   */
  public String getOrganizationId() {
    return this.organizationId;
  }

  /**
   * @param organizationId organizationId to set
   * @return EmployeeCreate
   */
  public <T extends EmployeeCreate> T setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
    return (T) this;
  }

  /**
   * @return qualification
   */
  @JsonIgnore
  public Qualification getQualification() {
    return this.qualification;
  }

  /**
   * @param qualification qualification to set
   * @return EmployeeCreate
   */
  public <T extends EmployeeCreate> T setQualification(Qualification qualification) {
    this.qualification = qualification;
    return (T) this;
  }

  /**
   * @return qualificationId
   */
  public String getQualificationId() {
    return this.qualificationId;
  }

  /**
   * @param qualificationId qualificationId to set
   * @return EmployeeCreate
   */
  public <T extends EmployeeCreate> T setQualificationId(String qualificationId) {
    this.qualificationId = qualificationId;
    return (T) this;
  }
}
