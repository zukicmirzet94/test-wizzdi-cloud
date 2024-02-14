package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class OrganizationType extends Basic {

  @OneToMany(targetEntity = Organization.class, mappedBy = "organizationType")
  @JsonIgnore
  private List<Organization> organizationTypeOrganizations;

  /**
   * @return organizationTypeOrganizations
   */
  @OneToMany(targetEntity = Organization.class, mappedBy = "organizationType")
  @JsonIgnore
  public List<Organization> getOrganizationTypeOrganizations() {
    return this.organizationTypeOrganizations;
  }

  /**
   * @param organizationTypeOrganizations organizationTypeOrganizations to set
   * @return OrganizationType
   */
  public <T extends OrganizationType> T setOrganizationTypeOrganizations(
      List<Organization> organizationTypeOrganizations) {
    this.organizationTypeOrganizations = organizationTypeOrganizations;
    return (T) this;
  }
}
