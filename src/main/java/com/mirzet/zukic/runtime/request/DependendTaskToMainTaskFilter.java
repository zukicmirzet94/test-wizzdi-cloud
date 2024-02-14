package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List DependendTaskToMainTask */
@IdValid.List({
  @IdValid(targetField = "tasks", field = "taskIds", fieldType = Task.class),
  @IdValid(targetField = "dependTaskses", field = "dependTasksIds", fieldType = DependTasks.class)
})
public class DependendTaskToMainTaskFilter {

  @Min(value = 0)
  private Integer currentPage;

  private Set<String> dependTasksIds;

  @JsonIgnore private List<DependTasks> dependTaskses;

  private Set<String> id;

  @Min(value = 1)
  private Integer pageSize;

  private Set<String> taskIds;

  @JsonIgnore private List<Task> tasks;

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage currentPage to set
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return dependTasksIds
   */
  public Set<String> getDependTasksIds() {
    return this.dependTasksIds;
  }

  /**
   * @param dependTasksIds dependTasksIds to set
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setDependTasksIds(Set<String> dependTasksIds) {
    this.dependTasksIds = dependTasksIds;
    return (T) this;
  }

  /**
   * @return dependTaskses
   */
  @JsonIgnore
  public List<DependTasks> getDependTaskses() {
    return this.dependTaskses;
  }

  /**
   * @param dependTaskses dependTaskses to set
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setDependTaskses(
      List<DependTasks> dependTaskses) {
    this.dependTaskses = dependTaskses;
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
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setId(Set<String> id) {
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
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }

  /**
   * @return taskIds
   */
  public Set<String> getTaskIds() {
    return this.taskIds;
  }

  /**
   * @param taskIds taskIds to set
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setTaskIds(Set<String> taskIds) {
    this.taskIds = taskIds;
    return (T) this;
  }

  /**
   * @return tasks
   */
  @JsonIgnore
  public List<Task> getTasks() {
    return this.tasks;
  }

  /**
   * @param tasks tasks to set
   * @return DependendTaskToMainTaskFilter
   */
  public <T extends DependendTaskToMainTaskFilter> T setTasks(List<Task> tasks) {
    this.tasks = tasks;
    return (T) this;
  }
}
