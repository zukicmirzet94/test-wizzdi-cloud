package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Task extends Basic {

  private int storyPoints;

  @OneToMany(targetEntity = Activity.class, mappedBy = "task")
  @JsonIgnore
  private List<Activity> taskActivities;

  @OneToMany(targetEntity = DependendTaskToMainTask.class, mappedBy = "task")
  @JsonIgnore
  private List<DependendTaskToMainTask> taskDependendTaskToMainTasks;

  @ManyToOne(targetEntity = Resource.class)
  private Resource resource;

  private TaskStatus taskStatus;

  @ManyToOne(targetEntity = Project.class)
  private Project project;

  /**
   * @return storyPoints
   */
  public int getStoryPoints() {
    return this.storyPoints;
  }

  /**
   * @param storyPoints storyPoints to set
   * @return Task
   */
  public <T extends Task> T setStoryPoints(int storyPoints) {
    this.storyPoints = storyPoints;
    return (T) this;
  }

  /**
   * @return taskActivities
   */
  @OneToMany(targetEntity = Activity.class, mappedBy = "task")
  @JsonIgnore
  public List<Activity> getTaskActivities() {
    return this.taskActivities;
  }

  /**
   * @param taskActivities taskActivities to set
   * @return Task
   */
  public <T extends Task> T setTaskActivities(List<Activity> taskActivities) {
    this.taskActivities = taskActivities;
    return (T) this;
  }

  /**
   * @return taskDependendTaskToMainTasks
   */
  @OneToMany(targetEntity = DependendTaskToMainTask.class, mappedBy = "task")
  @JsonIgnore
  public List<DependendTaskToMainTask> getTaskDependendTaskToMainTasks() {
    return this.taskDependendTaskToMainTasks;
  }

  /**
   * @param taskDependendTaskToMainTasks taskDependendTaskToMainTasks to set
   * @return Task
   */
  public <T extends Task> T setTaskDependendTaskToMainTasks(
      List<DependendTaskToMainTask> taskDependendTaskToMainTasks) {
    this.taskDependendTaskToMainTasks = taskDependendTaskToMainTasks;
    return (T) this;
  }

  /**
   * @return resource
   */
  @ManyToOne(targetEntity = Resource.class)
  public Resource getResource() {
    return this.resource;
  }

  /**
   * @param resource resource to set
   * @return Task
   */
  public <T extends Task> T setResource(Resource resource) {
    this.resource = resource;
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
   * @return Task
   */
  public <T extends Task> T setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
    return (T) this;
  }

  /**
   * @return project
   */
  @ManyToOne(targetEntity = Project.class)
  public Project getProject() {
    return this.project;
  }

  /**
   * @param project project to set
   * @return Task
   */
  public <T extends Task> T setProject(Project project) {
    this.project = project;
    return (T) this;
  }
}
