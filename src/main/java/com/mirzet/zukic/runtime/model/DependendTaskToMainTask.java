package com.mirzet.zukic.runtime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class DependendTaskToMainTask {

  @ManyToOne(targetEntity = DependTasks.class)
  private DependTasks dependTasks;

  @Id private String id;

  @ManyToOne(targetEntity = Task.class)
  private Task task;

  /**
   * @return dependTasks
   */
  @ManyToOne(targetEntity = DependTasks.class)
  public DependTasks getDependTasks() {
    return this.dependTasks;
  }

  /**
   * @param dependTasks dependTasks to set
   * @return DependendTaskToMainTask
   */
  public <T extends DependendTaskToMainTask> T setDependTasks(DependTasks dependTasks) {
    this.dependTasks = dependTasks;
    return (T) this;
  }

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return DependendTaskToMainTask
   */
  public <T extends DependendTaskToMainTask> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return task
   */
  @ManyToOne(targetEntity = Task.class)
  public Task getTask() {
    return this.task;
  }

  /**
   * @param task task to set
   * @return DependendTaskToMainTask
   */
  public <T extends DependendTaskToMainTask> T setTask(Task task) {
    this.task = task;
    return (T) this;
  }
}
