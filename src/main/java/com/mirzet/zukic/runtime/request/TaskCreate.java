package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.TaskStatus;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create Task */
@IdValid.List({
  @IdValid(
      targetField = "resource",
      field = "resourceId",
      fieldType = Resource.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "project",
      field = "projectId",
      fieldType = Project.class,
      groups = {Update.class, Create.class})
})
public class TaskCreate extends BasicCreate {

  @JsonIgnore private Project project;

  private String projectId;

  @JsonIgnore private Resource resource;

  private String resourceId;

  private Integer storyPoints;

  private TaskStatus taskStatus;

  /**
   * @return project
   */
  @JsonIgnore
  public Project getProject() {
    return this.project;
  }

  /**
   * @param project project to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setProject(Project project) {
    this.project = project;
    return (T) this;
  }

  /**
   * @return projectId
   */
  public String getProjectId() {
    return this.projectId;
  }

  /**
   * @param projectId projectId to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setProjectId(String projectId) {
    this.projectId = projectId;
    return (T) this;
  }

  /**
   * @return resource
   */
  @JsonIgnore
  public Resource getResource() {
    return this.resource;
  }

  /**
   * @param resource resource to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setResource(Resource resource) {
    this.resource = resource;
    return (T) this;
  }

  /**
   * @return resourceId
   */
  public String getResourceId() {
    return this.resourceId;
  }

  /**
   * @param resourceId resourceId to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setResourceId(String resourceId) {
    this.resourceId = resourceId;
    return (T) this;
  }

  /**
   * @return storyPoints
   */
  public Integer getStoryPoints() {
    return this.storyPoints;
  }

  /**
   * @param storyPoints storyPoints to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setStoryPoints(Integer storyPoints) {
    this.storyPoints = storyPoints;
    return (T) this;
  }

  /**
   * @return taskStatus
   */
  public TaskStatus getTaskStatus() {
    return this.taskStatus;
  }

  /**
   * @param taskStatus taskStatus to set
   * @return TaskCreate
   */
  public <T extends TaskCreate> T setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
    return (T) this;
  }
}
