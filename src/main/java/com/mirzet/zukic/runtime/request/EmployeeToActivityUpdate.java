package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update EmployeeToActivity */
@IdValid.List({
  @IdValid(
      targetField = "employeeToActivity",
      field = "id",
      fieldType = EmployeeToActivity.class,
      groups = {Update.class})
})
public class EmployeeToActivityUpdate extends EmployeeToActivityCreate {

  @JsonIgnore private EmployeeToActivity employeeToActivity;

  private String id;

  /**
   * @return employeeToActivity
   */
  @JsonIgnore
  public EmployeeToActivity getEmployeeToActivity() {
    return this.employeeToActivity;
  }

  /**
   * @param employeeToActivity employeeToActivity to set
   * @return EmployeeToActivityUpdate
   */
  public <T extends EmployeeToActivityUpdate> T setEmployeeToActivity(
      EmployeeToActivity employeeToActivity) {
    this.employeeToActivity = employeeToActivity;
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
   * @return EmployeeToActivityUpdate
   */
  public <T extends EmployeeToActivityUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
