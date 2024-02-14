package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.TaskStatus;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Task */
@IdValid.List({
  @IdValid(targetField = "resources", field = "resourceIds", fieldType = Resource.class),
  @IdValid(
      targetField = "taskDependendTaskToMainTaskses",
      field = "taskDependendTaskToMainTasksIds",
      fieldType = DependendTaskToMainTask.class),
  @IdValid(targetField = "projects", field = "projectIds", fieldType = Project.class),
  @IdValid(
      targetField = "taskActivitieses",
      field = "taskActivitiesIds",
      fieldType = Activity.class)
})
public class TaskFilter extends BasicFilter {

  private Set<String> projectIds;

  @JsonIgnore private List<Project> projects;

  private Set<String> resourceIds;

  @JsonIgnore private List<Resource> resources;

  private Integer storyPointsEnd;

  private Integer storyPointsStart;

  private Set<String> taskActivitiesIds;

  @JsonIgnore private List<Activity> taskActivitieses;

  private Set<String> taskDependendTaskToMainTasksIds;

  @JsonIgnore private List<DependendTaskToMainTask> taskDependendTaskToMainTaskses;

  private Set<TaskStatus> taskStatus;

  /**
   * @return projectIds
   */
  public Set<String> getProjectIds() {
    return this.projectIds;
  }

  /**
   * @param projectIds projectIds to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setProjectIds(Set<String> projectIds) {
    this.projectIds = projectIds;
    return (T) this;
  }

  /**
   * @return projects
   */
  @JsonIgnore
  public List<Project> getProjects() {
    return this.projects;
  }

  /**
   * @param projects projects to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setProjects(List<Project> projects) {
    this.projects = projects;
    return (T) this;
  }

  /**
   * @return resourceIds
   */
  public Set<String> getResourceIds() {
    return this.resourceIds;
  }

  /**
   * @param resourceIds resourceIds to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setResourceIds(Set<String> resourceIds) {
    this.resourceIds = resourceIds;
    return (T) this;
  }

  /**
   * @return resources
   */
  @JsonIgnore
  public List<Resource> getResources() {
    return this.resources;
  }

  /**
   * @param resources resources to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setResources(List<Resource> resources) {
    this.resources = resources;
    return (T) this;
  }

  /**
   * @return storyPointsEnd
   */
  public Integer getStoryPointsEnd() {
    return this.storyPointsEnd;
  }

  /**
   * @param storyPointsEnd storyPointsEnd to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setStoryPointsEnd(Integer storyPointsEnd) {
    this.storyPointsEnd = storyPointsEnd;
    return (T) this;
  }

  /**
   * @return storyPointsStart
   */
  public Integer getStoryPointsStart() {
    return this.storyPointsStart;
  }

  /**
   * @param storyPointsStart storyPointsStart to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setStoryPointsStart(Integer storyPointsStart) {
    this.storyPointsStart = storyPointsStart;
    return (T) this;
  }

  /**
   * @return taskActivitiesIds
   */
  public Set<String> getTaskActivitiesIds() {
    return this.taskActivitiesIds;
  }

  /**
   * @param taskActivitiesIds taskActivitiesIds to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setTaskActivitiesIds(Set<String> taskActivitiesIds) {
    this.taskActivitiesIds = taskActivitiesIds;
    return (T) this;
  }

  /**
   * @return taskActivitieses
   */
  @JsonIgnore
  public List<Activity> getTaskActivitieses() {
    return this.taskActivitieses;
  }

  /**
   * @param taskActivitieses taskActivitieses to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setTaskActivitieses(List<Activity> taskActivitieses) {
    this.taskActivitieses = taskActivitieses;
    return (T) this;
  }

  /**
   * @return taskDependendTaskToMainTasksIds
   */
  public Set<String> getTaskDependendTaskToMainTasksIds() {
    return this.taskDependendTaskToMainTasksIds;
  }

  /**
   * @param taskDependendTaskToMainTasksIds taskDependendTaskToMainTasksIds to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setTaskDependendTaskToMainTasksIds(
      Set<String> taskDependendTaskToMainTasksIds) {
    this.taskDependendTaskToMainTasksIds = taskDependendTaskToMainTasksIds;
    return (T) this;
  }

  /**
   * @return taskDependendTaskToMainTaskses
   */
  @JsonIgnore
  public List<DependendTaskToMainTask> getTaskDependendTaskToMainTaskses() {
    return this.taskDependendTaskToMainTaskses;
  }

  /**
   * @param taskDependendTaskToMainTaskses taskDependendTaskToMainTaskses to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setTaskDependendTaskToMainTaskses(
      List<DependendTaskToMainTask> taskDependendTaskToMainTaskses) {
    this.taskDependendTaskToMainTaskses = taskDependendTaskToMainTaskses;
    return (T) this;
  }

  /**
   * @return taskStatus
   */
  public Set<TaskStatus> getTaskStatus() {
    return this.taskStatus;
  }

  /**
   * @param taskStatus taskStatus to set
   * @return TaskFilter
   */
  public <T extends TaskFilter> T setTaskStatus(Set<TaskStatus> taskStatus) {
    this.taskStatus = taskStatus;
    return (T) this;
  }
}
