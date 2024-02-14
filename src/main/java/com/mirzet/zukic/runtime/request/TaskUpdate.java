package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Task */
@IdValid.List({
  @IdValid(
      targetField = "task",
      field = "id",
      fieldType = Task.class,
      groups = {Update.class})
})
public class TaskUpdate extends TaskCreate {

  private String id;

  @JsonIgnore private Task task;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return TaskUpdate
   */
  public <T extends TaskUpdate> T setId(String id) {
    this.id = id;
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
   * @return TaskUpdate
   */
  public <T extends TaskUpdate> T setTask(Task task) {
    this.task = task;
    return (T) this;
  }
}
