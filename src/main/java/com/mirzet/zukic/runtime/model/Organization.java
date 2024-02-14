package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Organization extends Basic {

  private String country;

  @OneToMany(targetEntity = Employee.class, mappedBy = "organization")
  @JsonIgnore
  private List<Employee> organizationEmployees;

  private String type;

  @ManyToOne(targetEntity = OrganizationType.class)
  private OrganizationType organizationType;

  /**
   * @return country
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * @param country country to set
   * @return Organization
   */
  public <T extends Organization> T setCountry(String country) {
    this.country = country;
    return (T) this;
  }

  /**
   * @return organizationEmployees
   */
  @OneToMany(targetEntity = Employee.class, mappedBy = "organization")
  @JsonIgnore
  public List<Employee> getOrganizationEmployees() {
    return this.organizationEmployees;
  }

  /**
   * @param organizationEmployees organizationEmployees to set
   * @return Organization
   */
  public <T extends Organization> T setOrganizationEmployees(List<Employee> organizationEmployees) {
    this.organizationEmployees = organizationEmployees;
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
   * @return Organization
   */
  public <T extends Organization> T setType(String type) {
    this.type = type;
    return (T) this;
  }

  /**
   * @return organizationType
   */
  @ManyToOne(targetEntity = OrganizationType.class)
  public OrganizationType getOrganizationType() {
    return this.organizationType;
  }

  /**
   * @param organizationType organizationType to set
   * @return Organization
   */
  public <T extends Organization> T setOrganizationType(OrganizationType organizationType) {
    this.organizationType = organizationType;
    return (T) this;
  }
}
