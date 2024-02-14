package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Organization */
@IdValid.List({
  @IdValid(
      targetField = "organizationTypes",
      field = "organizationTypeIds",
      fieldType = OrganizationType.class),
  @IdValid(
      targetField = "organizationEmployeeses",
      field = "organizationEmployeesIds",
      fieldType = Employee.class)
})
public class OrganizationFilter extends BasicFilter {

  private Set<String> country;

  private Set<String> organizationEmployeesIds;

  @JsonIgnore private List<Employee> organizationEmployeeses;

  private Set<String> organizationTypeIds;

  @JsonIgnore private List<OrganizationType> organizationTypes;

  private Set<String> type;

  /**
   * @return country
   */
  public Set<String> getCountry() {
    return this.country;
  }

  /**
   * @param country country to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setCountry(Set<String> country) {
    this.country = country;
    return (T) this;
  }

  /**
   * @return organizationEmployeesIds
   */
  public Set<String> getOrganizationEmployeesIds() {
    return this.organizationEmployeesIds;
  }

  /**
   * @param organizationEmployeesIds organizationEmployeesIds to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setOrganizationEmployeesIds(
      Set<String> organizationEmployeesIds) {
    this.organizationEmployeesIds = organizationEmployeesIds;
    return (T) this;
  }

  /**
   * @return organizationEmployeeses
   */
  @JsonIgnore
  public List<Employee> getOrganizationEmployeeses() {
    return this.organizationEmployeeses;
  }

  /**
   * @param organizationEmployeeses organizationEmployeeses to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setOrganizationEmployeeses(
      List<Employee> organizationEmployeeses) {
    this.organizationEmployeeses = organizationEmployeeses;
    return (T) this;
  }

  /**
   * @return organizationTypeIds
   */
  public Set<String> getOrganizationTypeIds() {
    return this.organizationTypeIds;
  }

  /**
   * @param organizationTypeIds organizationTypeIds to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setOrganizationTypeIds(Set<String> organizationTypeIds) {
    this.organizationTypeIds = organizationTypeIds;
    return (T) this;
  }

  /**
   * @return organizationTypes
   */
  @JsonIgnore
  public List<OrganizationType> getOrganizationTypes() {
    return this.organizationTypes;
  }

  /**
   * @param organizationTypes organizationTypes to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setOrganizationTypes(
      List<OrganizationType> organizationTypes) {
    this.organizationTypes = organizationTypes;
    return (T) this;
  }

  /**
   * @return type
   */
  public Set<String> getType() {
    return this.type;
  }

  /**
   * @param type type to set
   * @return OrganizationFilter
   */
  public <T extends OrganizationFilter> T setType(Set<String> type) {
    this.type = type;
    return (T) this;
  }
}
