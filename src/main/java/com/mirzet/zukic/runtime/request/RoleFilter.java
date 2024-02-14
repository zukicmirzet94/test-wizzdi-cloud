package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Role */
@IdValid.List({
  @IdValid(
      targetField = "roleEmployeeToActivitieses",
      field = "roleEmployeeToActivitiesIds",
      fieldType = EmployeeToActivity.class)
})
public class RoleFilter extends BasicFilter {

  private Set<String> roleEmployeeToActivitiesIds;

  @JsonIgnore private List<EmployeeToActivity> roleEmployeeToActivitieses;

  /**
   * @return roleEmployeeToActivitiesIds
   */
  public Set<String> getRoleEmployeeToActivitiesIds() {
    return this.roleEmployeeToActivitiesIds;
  }

  /**
   * @param roleEmployeeToActivitiesIds roleEmployeeToActivitiesIds to set
   * @return RoleFilter
   */
  public <T extends RoleFilter> T setRoleEmployeeToActivitiesIds(
      Set<String> roleEmployeeToActivitiesIds) {
    this.roleEmployeeToActivitiesIds = roleEmployeeToActivitiesIds;
    return (T) this;
  }

  /**
   * @return roleEmployeeToActivitieses
   */
  @JsonIgnore
  public List<EmployeeToActivity> getRoleEmployeeToActivitieses() {
    return this.roleEmployeeToActivitieses;
  }

  /**
   * @param roleEmployeeToActivitieses roleEmployeeToActivitieses to set
   * @return RoleFilter
   */
  public <T extends RoleFilter> T setRoleEmployeeToActivitieses(
      List<EmployeeToActivity> roleEmployeeToActivitieses) {
    this.roleEmployeeToActivitieses = roleEmployeeToActivitieses;
    return (T) this;
  }
}
