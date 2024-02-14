package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List OrganizationType */
@IdValid.List({
  @IdValid(
      targetField = "organizationTypeOrganizationses",
      field = "organizationTypeOrganizationsIds",
      fieldType = Organization.class)
})
public class OrganizationTypeFilter extends BasicFilter {

  private Set<String> organizationTypeOrganizationsIds;

  @JsonIgnore private List<Organization> organizationTypeOrganizationses;

  /**
   * @return organizationTypeOrganizationsIds
   */
  public Set<String> getOrganizationTypeOrganizationsIds() {
    return this.organizationTypeOrganizationsIds;
  }

  /**
   * @param organizationTypeOrganizationsIds organizationTypeOrganizationsIds to set
   * @return OrganizationTypeFilter
   */
  public <T extends OrganizationTypeFilter> T setOrganizationTypeOrganizationsIds(
      Set<String> organizationTypeOrganizationsIds) {
    this.organizationTypeOrganizationsIds = organizationTypeOrganizationsIds;
    return (T) this;
  }

  /**
   * @return organizationTypeOrganizationses
   */
  @JsonIgnore
  public List<Organization> getOrganizationTypeOrganizationses() {
    return this.organizationTypeOrganizationses;
  }

  /**
   * @param organizationTypeOrganizationses organizationTypeOrganizationses to set
   * @return OrganizationTypeFilter
   */
  public <T extends OrganizationTypeFilter> T setOrganizationTypeOrganizationses(
      List<Organization> organizationTypeOrganizationses) {
    this.organizationTypeOrganizationses = organizationTypeOrganizationses;
    return (T) this;
  }
}
