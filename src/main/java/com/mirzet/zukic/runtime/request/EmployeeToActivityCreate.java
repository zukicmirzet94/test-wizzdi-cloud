package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create EmployeeToActivity */
@IdValid.List({
  @IdValid(
      targetField = "employee",
      field = "employeeId",
      fieldType = Employee.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "activity",
      field = "activityId",
      fieldType = Activity.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "role",
      field = "roleId",
      fieldType = Role.class,
      groups = {Update.class, Create.class})
})
public class EmployeeToActivityCreate {

  @JsonIgnore private Activity activity;

  private String activityId;

  @JsonIgnore private Employee employee;

  private String employeeId;

  @JsonIgnore private Role role;

  private String roleId;

  /**
   * @return activity
   */
  @JsonIgnore
  public Activity getActivity() {
    return this.activity;
  }

  /**
   * @param activity activity to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setActivity(Activity activity) {
    this.activity = activity;
    return (T) this;
  }

  /**
   * @return activityId
   */
  public String getActivityId() {
    return this.activityId;
  }

  /**
   * @param activityId activityId to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setActivityId(String activityId) {
    this.activityId = activityId;
    return (T) this;
  }

  /**
   * @return employee
   */
  @JsonIgnore
  public Employee getEmployee() {
    return this.employee;
  }

  /**
   * @param employee employee to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setEmployee(Employee employee) {
    this.employee = employee;
    return (T) this;
  }

  /**
   * @return employeeId
   */
  public String getEmployeeId() {
    return this.employeeId;
  }

  /**
   * @param employeeId employeeId to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
    return (T) this;
  }

  /**
   * @return role
   */
  @JsonIgnore
  public Role getRole() {
    return this.role;
  }

  /**
   * @param role role to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setRole(Role role) {
    this.role = role;
    return (T) this;
  }

  /**
   * @return roleId
   */
  public String getRoleId() {
    return this.roleId;
  }

  /**
   * @param roleId roleId to set
   * @return EmployeeToActivityCreate
   */
  public <T extends EmployeeToActivityCreate> T setRoleId(String roleId) {
    this.roleId = roleId;
    return (T) this;
  }
}
