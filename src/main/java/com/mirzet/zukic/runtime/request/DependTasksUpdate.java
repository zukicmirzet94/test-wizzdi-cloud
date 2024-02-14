package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update DependTasks */
@IdValid.List({
  @IdValid(
      targetField = "dependTasks",
      field = "id",
      fieldType = DependTasks.class,
      groups = {Update.class})
})
public class DependTasksUpdate extends DependTasksCreate {

  @JsonIgnore private DependTasks dependTasks;

  private String id;

  /**
   * @return dependTasks
   */
  @JsonIgnore
  public DependTasks getDependTasks() {
    return this.dependTasks;
  }

  /**
   * @param dependTasks dependTasks to set
   * @return DependTasksUpdate
   */
  public <T extends DependTasksUpdate> T setDependTasks(DependTasks dependTasks) {
    this.dependTasks = dependTasks;
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
   * @return DependTasksUpdate
   */
  public <T extends DependTasksUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
