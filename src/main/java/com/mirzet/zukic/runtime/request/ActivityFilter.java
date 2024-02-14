package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.Priority;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

/** Object Used to List Activity */
@IdValid.List({
  @IdValid(
      targetField = "activityEmployeeToActivitieses",
      field = "activityEmployeeToActivitiesIds",
      fieldType = EmployeeToActivity.class),
  @IdValid(targetField = "tasks", field = "taskIds", fieldType = Task.class)
})
public class ActivityFilter extends BasicFilter {

  private Set<String> activityEmployeeToActivitiesIds;

  @JsonIgnore private List<EmployeeToActivity> activityEmployeeToActivitieses;

  private Double actualBudgetEnd;

  private Double actualBudgetStart;

  private OffsetDateTime actualEndDateEnd;

  private OffsetDateTime actualEndDateStart;

  private OffsetDateTime actualStartDateEnd;

  private OffsetDateTime actualStartDateStart;

  private Double plannedBudgetEnd;

  private Double plannedBudgetStart;

  private OffsetDateTime plannedEndDateEnd;

  private OffsetDateTime plannedEndDateStart;

  private OffsetDateTime plannedStartDateEnd;

  private OffsetDateTime plannedStartDateStart;

  private Set<Priority> priority;

  private Set<String> taskIds;

  @JsonIgnore private List<Task> tasks;

  /**
   * @return activityEmployeeToActivitiesIds
   */
  public Set<String> getActivityEmployeeToActivitiesIds() {
    return this.activityEmployeeToActivitiesIds;
  }

  /**
   * @param activityEmployeeToActivitiesIds activityEmployeeToActivitiesIds to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActivityEmployeeToActivitiesIds(
      Set<String> activityEmployeeToActivitiesIds) {
    this.activityEmployeeToActivitiesIds = activityEmployeeToActivitiesIds;
    return (T) this;
  }

  /**
   * @return activityEmployeeToActivitieses
   */
  @JsonIgnore
  public List<EmployeeToActivity> getActivityEmployeeToActivitieses() {
    return this.activityEmployeeToActivitieses;
  }

  /**
   * @param activityEmployeeToActivitieses activityEmployeeToActivitieses to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActivityEmployeeToActivitieses(
      List<EmployeeToActivity> activityEmployeeToActivitieses) {
    this.activityEmployeeToActivitieses = activityEmployeeToActivitieses;
    return (T) this;
  }

  /**
   * @return actualBudgetEnd
   */
  public Double getActualBudgetEnd() {
    return this.actualBudgetEnd;
  }

  /**
   * @param actualBudgetEnd actualBudgetEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualBudgetEnd(Double actualBudgetEnd) {
    this.actualBudgetEnd = actualBudgetEnd;
    return (T) this;
  }

  /**
   * @return actualBudgetStart
   */
  public Double getActualBudgetStart() {
    return this.actualBudgetStart;
  }

  /**
   * @param actualBudgetStart actualBudgetStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualBudgetStart(Double actualBudgetStart) {
    this.actualBudgetStart = actualBudgetStart;
    return (T) this;
  }

  /**
   * @return actualEndDateEnd
   */
  public OffsetDateTime getActualEndDateEnd() {
    return this.actualEndDateEnd;
  }

  /**
   * @param actualEndDateEnd actualEndDateEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualEndDateEnd(OffsetDateTime actualEndDateEnd) {
    this.actualEndDateEnd = actualEndDateEnd;
    return (T) this;
  }

  /**
   * @return actualEndDateStart
   */
  public OffsetDateTime getActualEndDateStart() {
    return this.actualEndDateStart;
  }

  /**
   * @param actualEndDateStart actualEndDateStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualEndDateStart(OffsetDateTime actualEndDateStart) {
    this.actualEndDateStart = actualEndDateStart;
    return (T) this;
  }

  /**
   * @return actualStartDateEnd
   */
  public OffsetDateTime getActualStartDateEnd() {
    return this.actualStartDateEnd;
  }

  /**
   * @param actualStartDateEnd actualStartDateEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualStartDateEnd(OffsetDateTime actualStartDateEnd) {
    this.actualStartDateEnd = actualStartDateEnd;
    return (T) this;
  }

  /**
   * @return actualStartDateStart
   */
  public OffsetDateTime getActualStartDateStart() {
    return this.actualStartDateStart;
  }

  /**
   * @param actualStartDateStart actualStartDateStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setActualStartDateStart(OffsetDateTime actualStartDateStart) {
    this.actualStartDateStart = actualStartDateStart;
    return (T) this;
  }

  /**
   * @return plannedBudgetEnd
   */
  public Double getPlannedBudgetEnd() {
    return this.plannedBudgetEnd;
  }

  /**
   * @param plannedBudgetEnd plannedBudgetEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedBudgetEnd(Double plannedBudgetEnd) {
    this.plannedBudgetEnd = plannedBudgetEnd;
    return (T) this;
  }

  /**
   * @return plannedBudgetStart
   */
  public Double getPlannedBudgetStart() {
    return this.plannedBudgetStart;
  }

  /**
   * @param plannedBudgetStart plannedBudgetStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedBudgetStart(Double plannedBudgetStart) {
    this.plannedBudgetStart = plannedBudgetStart;
    return (T) this;
  }

  /**
   * @return plannedEndDateEnd
   */
  public OffsetDateTime getPlannedEndDateEnd() {
    return this.plannedEndDateEnd;
  }

  /**
   * @param plannedEndDateEnd plannedEndDateEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedEndDateEnd(OffsetDateTime plannedEndDateEnd) {
    this.plannedEndDateEnd = plannedEndDateEnd;
    return (T) this;
  }

  /**
   * @return plannedEndDateStart
   */
  public OffsetDateTime getPlannedEndDateStart() {
    return this.plannedEndDateStart;
  }

  /**
   * @param plannedEndDateStart plannedEndDateStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedEndDateStart(OffsetDateTime plannedEndDateStart) {
    this.plannedEndDateStart = plannedEndDateStart;
    return (T) this;
  }

  /**
   * @return plannedStartDateEnd
   */
  public OffsetDateTime getPlannedStartDateEnd() {
    return this.plannedStartDateEnd;
  }

  /**
   * @param plannedStartDateEnd plannedStartDateEnd to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedStartDateEnd(OffsetDateTime plannedStartDateEnd) {
    this.plannedStartDateEnd = plannedStartDateEnd;
    return (T) this;
  }

  /**
   * @return plannedStartDateStart
   */
  public OffsetDateTime getPlannedStartDateStart() {
    return this.plannedStartDateStart;
  }

  /**
   * @param plannedStartDateStart plannedStartDateStart to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPlannedStartDateStart(
      OffsetDateTime plannedStartDateStart) {
    this.plannedStartDateStart = plannedStartDateStart;
    return (T) this;
  }

  /**
   * @return priority
   */
  public Set<Priority> getPriority() {
    return this.priority;
  }

  /**
   * @param priority priority to set
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setPriority(Set<Priority> priority) {
    this.priority = priority;
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
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setTaskIds(Set<String> taskIds) {
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
   * @return ActivityFilter
   */
  public <T extends ActivityFilter> T setTasks(List<Task> tasks) {
    this.tasks = tasks;
    return (T) this;
  }
}
