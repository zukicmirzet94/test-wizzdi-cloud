package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Employee */
@IdValid.List({
  @IdValid(
      targetField = "qualifications",
      field = "qualificationIds",
      fieldType = Qualification.class),
  @IdValid(
      targetField = "organizations",
      field = "organizationIds",
      fieldType = Organization.class),
  @IdValid(
      targetField = "employeeEmployeeToActivitieses",
      field = "employeeEmployeeToActivitiesIds",
      fieldType = EmployeeToActivity.class)
})
public class EmployeeFilter extends PersonFilter {

  private Set<String> employeeEmployeeToActivitiesIds;

  @JsonIgnore private List<EmployeeToActivity> employeeEmployeeToActivitieses;

  private Set<String> organizationIds;

  @JsonIgnore private List<Organization> organizations;

  private Set<String> qualificationIds;

  @JsonIgnore private List<Qualification> qualifications;

  /**
   * @return employeeEmployeeToActivitiesIds
   */
  public Set<String> getEmployeeEmployeeToActivitiesIds() {
    return this.employeeEmployeeToActivitiesIds;
  }

  /**
   * @param employeeEmployeeToActivitiesIds employeeEmployeeToActivitiesIds to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setEmployeeEmployeeToActivitiesIds(
      Set<String> employeeEmployeeToActivitiesIds) {
    this.employeeEmployeeToActivitiesIds = employeeEmployeeToActivitiesIds;
    return (T) this;
  }

  /**
   * @return employeeEmployeeToActivitieses
   */
  @JsonIgnore
  public List<EmployeeToActivity> getEmployeeEmployeeToActivitieses() {
    return this.employeeEmployeeToActivitieses;
  }

  /**
   * @param employeeEmployeeToActivitieses employeeEmployeeToActivitieses to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setEmployeeEmployeeToActivitieses(
      List<EmployeeToActivity> employeeEmployeeToActivitieses) {
    this.employeeEmployeeToActivitieses = employeeEmployeeToActivitieses;
    return (T) this;
  }

  /**
   * @return organizationIds
   */
  public Set<String> getOrganizationIds() {
    return this.organizationIds;
  }

  /**
   * @param organizationIds organizationIds to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setOrganizationIds(Set<String> organizationIds) {
    this.organizationIds = organizationIds;
    return (T) this;
  }

  /**
   * @return organizations
   */
  @JsonIgnore
  public List<Organization> getOrganizations() {
    return this.organizations;
  }

  /**
   * @param organizations organizations to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setOrganizations(List<Organization> organizations) {
    this.organizations = organizations;
    return (T) this;
  }

  /**
   * @return qualificationIds
   */
  public Set<String> getQualificationIds() {
    return this.qualificationIds;
  }

  /**
   * @param qualificationIds qualificationIds to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setQualificationIds(Set<String> qualificationIds) {
    this.qualificationIds = qualificationIds;
    return (T) this;
  }

  /**
   * @return qualifications
   */
  @JsonIgnore
  public List<Qualification> getQualifications() {
    return this.qualifications;
  }

  /**
   * @param qualifications qualifications to set
   * @return EmployeeFilter
   */
  public <T extends EmployeeFilter> T setQualifications(List<Qualification> qualifications) {
    this.qualifications = qualifications;
    return (T) this;
  }
}
