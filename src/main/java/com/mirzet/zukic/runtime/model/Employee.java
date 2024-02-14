package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Employee extends Person {

  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "employee")
  @JsonIgnore
  private List<EmployeeToActivity> employeeEmployeeToActivities;

  @ManyToOne(targetEntity = Qualification.class)
  private Qualification qualification;

  @ManyToOne(targetEntity = Organization.class)
  private Organization organization;

  /**
   * @return employeeEmployeeToActivities
   */
  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "employee")
  @JsonIgnore
  public List<EmployeeToActivity> getEmployeeEmployeeToActivities() {
    return this.employeeEmployeeToActivities;
  }

  /**
   * @param employeeEmployeeToActivities employeeEmployeeToActivities to set
   * @return Employee
   */
  public <T extends Employee> T setEmployeeEmployeeToActivities(
      List<EmployeeToActivity> employeeEmployeeToActivities) {
    this.employeeEmployeeToActivities = employeeEmployeeToActivities;
    return (T) this;
  }

  /**
   * @return qualification
   */
  @ManyToOne(targetEntity = Qualification.class)
  public Qualification getQualification() {
    return this.qualification;
  }

  /**
   * @param qualification qualification to set
   * @return Employee
   */
  public <T extends Employee> T setQualification(Qualification qualification) {
    this.qualification = qualification;
    return (T) this;
  }

  /**
   * @return organization
   */
  @ManyToOne(targetEntity = Organization.class)
  public Organization getOrganization() {
    return this.organization;
  }

  /**
   * @param organization organization to set
   * @return Employee
   */
  public <T extends Employee> T setOrganization(Organization organization) {
    this.organization = organization;
    return (T) this;
  }
}
