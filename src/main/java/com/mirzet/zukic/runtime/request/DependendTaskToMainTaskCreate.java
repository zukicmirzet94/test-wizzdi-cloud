package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create DependendTaskToMainTask */
@IdValid.List({
  @IdValid(
      targetField = "task",
      field = "taskId",
      fieldType = Task.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "dependTasks",
      field = "dependTasksId",
      fieldType = DependTasks.class,
      groups = {Update.class, Create.class})
})
public class DependendTaskToMainTaskCreate {

  @JsonIgnore private DependTasks dependTasks;

  private String dependTasksId;

  @JsonIgnore private Task task;

  private String taskId;

  /**
   * @return dependTasks
   */
  @JsonIgnore
  public DependTasks getDependTasks() {
    return this.dependTasks;
  }

  /**
   * @param dependTasks dependTasks to set
   * @return DependendTaskToMainTaskCreate
   */
  public <T extends DependendTaskToMainTaskCreate> T setDependTasks(DependTasks dependTasks) {
    this.dependTasks = dependTasks;
    return (T) this;
  }

  /**
   * @return dependTasksId
   */
  public String getDependTasksId() {
    return this.dependTasksId;
  }

  /**
   * @param dependTasksId dependTasksId to set
   * @return DependendTaskToMainTaskCreate
   */
  public <T extends DependendTaskToMainTaskCreate> T setDependTasksId(String dependTasksId) {
    this.dependTasksId = dependTasksId;
    return (T) this;
  }

  /**
   * @return task
   */
  @JsonIgnore
  public Task getTask() {
    return this.task;
  }

  /**
   * @param task task to set
   * @return DependendTaskToMainTaskCreate
   */
  public <T extends DependendTaskToMainTaskCreate> T setTask(Task task) {
    this.task = task;
    return (T) this;
  }

  /**
   * @return taskId
   */
  public String getTaskId() {
    return this.taskId;
  }

  /**
   * @param taskId taskId to set
   * @return DependendTaskToMainTaskCreate
   */
  public <T extends DependendTaskToMainTaskCreate> T setTaskId(String taskId) {
    this.taskId = taskId;
    return (T) this;
  }
}
