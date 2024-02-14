package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Activity extends Basic {

  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "activity")
  @JsonIgnore
  private List<EmployeeToActivity> activityEmployeeToActivities;

  private Priority priority;

  private OffsetDateTime plannedStartDate;

  private OffsetDateTime plannedEndDate;

  private double plannedBudget;

  private OffsetDateTime actualStartDate;

  private OffsetDateTime actualEndDate;

  private double actualBudget;

  @ManyToOne(targetEntity = Task.class)
  private Task task;

  /**
   * @return activityEmployeeToActivities
   */
  @OneToMany(targetEntity = EmployeeToActivity.class, mappedBy = "activity")
  @JsonIgnore
  public List<EmployeeToActivity> getActivityEmployeeToActivities() {
    return this.activityEmployeeToActivities;
  }

  /**
   * @param activityEmployeeToActivities activityEmployeeToActivities to set
   * @return Activity
   */
  public <T extends Activity> T setActivityEmployeeToActivities(
      List<EmployeeToActivity> activityEmployeeToActivities) {
    this.activityEmployeeToActivities = activityEmployeeToActivities;
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
   * @return Activity
   */
  public <T extends Activity> T setPriority(Priority priority) {
    this.priority = priority;
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
   * @return Activity
   */
  public <T extends Activity> T setPlannedStartDate(OffsetDateTime plannedStartDate) {
    this.plannedStartDate = plannedStartDate;
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
   * @return Activity
   */
  public <T extends Activity> T setPlannedEndDate(OffsetDateTime plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
    return (T) this;
  }

  /**
   * @return plannedBudget
   */
  public double getPlannedBudget() {
    return this.plannedBudget;
  }

  /**
   * @param plannedBudget plannedBudget to set
   * @return Activity
   */
  public <T extends Activity> T setPlannedBudget(double plannedBudget) {
    this.plannedBudget = plannedBudget;
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
   * @return Activity
   */
  public <T extends Activity> T setActualStartDate(OffsetDateTime actualStartDate) {
    this.actualStartDate = actualStartDate;
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
   * @return Activity
   */
  public <T extends Activity> T setActualEndDate(OffsetDateTime actualEndDate) {
    this.actualEndDate = actualEndDate;
    return (T) this;
  }

  /**
   * @return actualBudget
   */
  public double getActualBudget() {
    return this.actualBudget;
  }

  /**
   * @param actualBudget actualBudget to set
   * @return Activity
   */
  public <T extends Activity> T setActualBudget(double actualBudget) {
    this.actualBudget = actualBudget;
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
   * @return Activity
   */
  public <T extends Activity> T setTask(Task task) {
    this.task = task;
    return (T) this;
  }
}
