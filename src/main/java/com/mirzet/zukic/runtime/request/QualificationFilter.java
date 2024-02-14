package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Qualification */
@IdValid.List({
  @IdValid(
      targetField = "qualificationEmployeeses",
      field = "qualificationEmployeesIds",
      fieldType = Employee.class)
})
public class QualificationFilter extends BasicFilter {

  private Set<String> qualificationEmployeesIds;

  @JsonIgnore private List<Employee> qualificationEmployeeses;

  /**
   * @return qualificationEmployeesIds
   */
  public Set<String> getQualificationEmployeesIds() {
    return this.qualificationEmployeesIds;
  }

  /**
   * @param qualificationEmployeesIds qualificationEmployeesIds to set
   * @return QualificationFilter
   */
  public <T extends QualificationFilter> T setQualificationEmployeesIds(
      Set<String> qualificationEmployeesIds) {
    this.qualificationEmployeesIds = qualificationEmployeesIds;
    return (T) this;
  }

  /**
   * @return qualificationEmployeeses
   */
  @JsonIgnore
  public List<Employee> getQualificationEmployeeses() {
    return this.qualificationEmployeeses;
  }

  /**
   * @param qualificationEmployeeses qualificationEmployeeses to set
   * @return QualificationFilter
   */
  public <T extends QualificationFilter> T setQualificationEmployeeses(
      List<Employee> qualificationEmployeeses) {
    this.qualificationEmployeeses = qualificationEmployeeses;
    return (T) this;
  }
}
