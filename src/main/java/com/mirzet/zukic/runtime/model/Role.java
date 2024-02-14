package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Role extends Basic {

  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "role")
  @JsonIgnore
  private List<EmployeeToActivity> roleEmployeeToActivities;

  /**
   * @return roleEmployeeToActivities
   */
  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "role")
  @JsonIgnore
  public List<EmployeeToActivity> getRoleEmployeeToActivities() {
    return this.roleEmployeeToActivities;
  }

  /**
   * @param roleEmployeeToActivities roleEmployeeToActivities to set
   * @return Role
   */
  public <T extends Role> T setRoleEmployeeToActivities(
      List<EmployeeToActivity> roleEmployeeToActivities) {
    this.roleEmployeeToActivities = roleEmployeeToActivities;
    return (T) this;
  }
}
