package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Employee */
@IdValid.List({
  @IdValid(
      targetField = "employee",
      field = "id",
      fieldType = Employee.class,
      groups = {Update.class})
})
public class EmployeeUpdate extends EmployeeCreate {

  @JsonIgnore private Employee employee;

  private String id;

  /**
   * @return employee
   */
  @JsonIgnore
  public Employee getEmployee() {
    return this.employee;
  }

  /**
   * @param employee employee to set
   * @return EmployeeUpdate
   */
  public <T extends EmployeeUpdate> T setEmployee(Employee employee) {
    this.employee = employee;
    return (T) this;
  }

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return EmployeeUpdate
   */
  public <T extends EmployeeUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
