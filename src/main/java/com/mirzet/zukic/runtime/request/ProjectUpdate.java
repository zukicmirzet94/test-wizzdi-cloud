package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Project */
@IdValid.List({
  @IdValid(
      targetField = "project",
      field = "id",
      fieldType = Project.class,
      groups = {Update.class})
})
public class ProjectUpdate extends ProjectCreate {

  private String id;

  @JsonIgnore private Project project;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return ProjectUpdate
   */
  public <T extends ProjectUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return project
   */
  @JsonIgnore
  public Project getProject() {
    return this.project;
  }

  /**
   * @param project project to set
   * @return ProjectUpdate
   */
  public <T extends ProjectUpdate> T setProject(Project project) {
    this.project = project;
    return (T) this;
  }
}
