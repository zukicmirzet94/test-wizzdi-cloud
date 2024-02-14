package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List EmployeeToActivity */
@IdValid.List({
  @IdValid(targetField = "employees", field = "employeeIds", fieldType = Employee.class),
  @IdValid(targetField = "activities", field = "activityIds", fieldType = Activity.class),
  @IdValid(targetField = "roles", field = "roleIds", fieldType = Role.class)
})
public class EmployeeToActivityFilter {

  @JsonIgnore private List<Activity> activities;

  private Set<String> activityIds;

  @Min(value = 0)
  private Integer currentPage;

  private Set<String> employeeIds;

  @JsonIgnore private List<Employee> employees;

  private Set<String> id;

  @Min(value = 1)
  private Integer pageSize;

  private Set<String> roleIds;

  @JsonIgnore private List<Role> roles;

  /**
   * @return activities
   */
  @JsonIgnore
  public List<Activity> getActivities() {
    return this.activities;
  }

  /**
   * @param activities activities to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setActivities(List<Activity> activities) {
    this.activities = activities;
    return (T) this;
  }

  /**
   * @return activityIds
   */
  public Set<String> getActivityIds() {
    return this.activityIds;
  }

  /**
   * @param activityIds activityIds to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setActivityIds(Set<String> activityIds) {
    this.activityIds = activityIds;
    return (T) this;
  }

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage currentPage to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return employeeIds
   */
  public Set<String> getEmployeeIds() {
    return this.employeeIds;
  }

  /**
   * @param employeeIds employeeIds to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setEmployeeIds(Set<String> employeeIds) {
    this.employeeIds = employeeIds;
    return (T) this;
  }

  /**
   * @return employees
   */
  @JsonIgnore
  public List<Employee> getEmployees() {
    return this.employees;
  }

  /**
   * @param employees employees to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setEmployees(List<Employee> employees) {
    this.employees = employees;
    return (T) this;
  }

  /**
   * @return id
   */
  public Set<String> getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return pageSize
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param pageSize pageSize to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }

  /**
   * @return roleIds
   */
  public Set<String> getRoleIds() {
    return this.roleIds;
  }

  /**
   * @param roleIds roleIds to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setRoleIds(Set<String> roleIds) {
    this.roleIds = roleIds;
    return (T) this;
  }

  /**
   * @return roles
   */
  @JsonIgnore
  public List<Role> getRoles() {
    return this.roles;
  }

  /**
   * @param roles roles to set
   * @return EmployeeToActivityFilter
   */
  public <T extends EmployeeToActivityFilter> T setRoles(List<Role> roles) {
    this.roles = roles;
    return (T) this;
  }
}
