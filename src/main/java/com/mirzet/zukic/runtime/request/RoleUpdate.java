package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Role */
@IdValid.List({
  @IdValid(
      targetField = "role",
      field = "id",
      fieldType = Role.class,
      groups = {Update.class})
})
public class RoleUpdate extends RoleCreate {

  private String id;

  @JsonIgnore private Role role;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return RoleUpdate
   */
  public <T extends RoleUpdate> T setId(String id) {
    this.id = id;
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
   * @return RoleUpdate
   */
  public <T extends RoleUpdate> T setRole(Role role) {
    this.role = role;
    return (T) this;
  }
}
