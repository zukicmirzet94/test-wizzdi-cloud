package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Qualification extends Basic {

  @OneToMany(targetEntity = Employee.class, mappedBy = "qualification")
  @JsonIgnore
  private List<Employee> qualificationEmployees;

  /**
   * @return qualificationEmployees
   */
  @OneToMany(targetEntity = Employee.class, mappedBy = "qualification")
  @JsonIgnore
  public List<Employee> getQualificationEmployees() {
    return this.qualificationEmployees;
  }

  /**
   * @param qualificationEmployees qualificationEmployees to set
   * @return Qualification
   */
  public <T extends Qualification> T setQualificationEmployees(
      List<Employee> qualificationEmployees) {
    this.qualificationEmployees = qualificationEmployees;
    return (T) this;
  }
}
