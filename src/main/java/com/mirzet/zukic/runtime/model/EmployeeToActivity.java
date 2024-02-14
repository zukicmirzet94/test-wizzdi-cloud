package com.mirzet.zukic.runtime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class EmployeeToActivity {

  @ManyToOne(targetEntity = Employee.class)
  private Employee employee;

  @Id private String id;

  @ManyToOne(targetEntity = Activity.class)
  private Activity activity;

  @ManyToOne(targetEntity = Role.class)
  private Role role;

  /**
   * @return employee
   */
  @ManyToOne(targetEntity = Employee.class)
  public Employee getEmployee() {
    return this.employee;
  }

  /**
   * @param employee employee to set
   * @return EmployeeToActivity
   */
  public <T extends EmployeeToActivity> T setEmployee(Employee employee) {
    this.employee = employee;
    return (T) this;
  }

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return EmployeeToActivity
   */
  public <T extends EmployeeToActivity> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return activity
   */
  @ManyToOne(targetEntity = Activity.class)
  public Activity getActivity() {
    return this.activity;
  }

  /**
   * @param activity activity to set
   * @return EmployeeToActivity
   */
  public <T extends EmployeeToActivity> T setActivity(Activity activity) {
    this.activity = activity;
    return (T) this;
  }

  /**
   * @return role
   */
  @ManyToOne(targetEntity = Role.class)
  public Role getRole() {
    return this.role;
  }

  /**
   * @param role role to set
   * @return EmployeeToActivity
   */
  public <T extends EmployeeToActivity> T setRole(Role role) {
    this.role = role;
    return (T) this;
  }
}
