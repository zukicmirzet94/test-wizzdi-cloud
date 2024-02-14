package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Priority;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;
import java.time.OffsetDateTime;

/** Object Used to Create Activity */
@IdValid.List({
  @IdValid(
      targetField = "task",
      field = "taskId",
      fieldType = Task.class,
      groups = {Update.class, Create.class})
})
public class ActivityCreate extends BasicCreate {

  private Double actualBudget;

  private OffsetDateTime actualEndDate;

  private OffsetDateTime actualStartDate;

  private Double plannedBudget;

  private OffsetDateTime plannedEndDate;

  private OffsetDateTime plannedStartDate;

  private Priority priority;

  @JsonIgnore private Task task;

  private String taskId;

  /**
   * @return actualBudget
   */
  public Double getActualBudget() {
    return this.actualBudget;
  }

  /**
   * @param actualBudget actualBudget to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setActualBudget(Double actualBudget) {
    this.actualBudget = actualBudget;
    return (T) this;
  }

  /**
   * @return actualEndDate
   */
  public OffsetDateTime getActualEndDate() {
    return this.actualEndDate;
  }

  /**
   * @param actualEndDate actualEndDate to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setActualEndDate(OffsetDateTime actualEndDate) {
    this.actualEndDate = actualEndDate;
    return (T) this;
  }

  /**
   * @return actualStartDate
   */
  public OffsetDateTime getActualStartDate() {
    return this.actualStartDate;
  }

  /**
   * @param actualStartDate actualStartDate to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setActualStartDate(OffsetDateTime actualStartDate) {
    this.actualStartDate = actualStartDate;
    return (T) this;
  }

  /**
   * @return plannedBudget
   */
  public Double getPlannedBudget() {
    return this.plannedBudget;
  }

  /**
   * @param plannedBudget plannedBudget to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setPlannedBudget(Double plannedBudget) {
    this.plannedBudget = plannedBudget;
    return (T) this;
  }

  /**
   * @return plannedEndDate
   */
  public OffsetDateTime getPlannedEndDate() {
    return this.plannedEndDate;
  }

  /**
   * @param plannedEndDate plannedEndDate to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setPlannedEndDate(OffsetDateTime plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
    return (T) this;
  }

  /**
   * @return plannedStartDate
   */
  public OffsetDateTime getPlannedStartDate() {
    return this.plannedStartDate;
  }

  /**
   * @param plannedStartDate plannedStartDate to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setPlannedStartDate(OffsetDateTime plannedStartDate) {
    this.plannedStartDate = plannedStartDate;
    return (T) this;
  }

  /**
   * @return priority
   */
  public Priority getPriority() {
    return this.priority;
  }

  /**
   * @param priority priority to set
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setPriority(Priority priority) {
    this.priority = priority;
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
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setTask(Task task) {
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
   * @return ActivityCreate
   */
  public <T extends ActivityCreate> T setTaskId(String taskId) {
    this.taskId = taskId;
    return (T) this;
  }
}
