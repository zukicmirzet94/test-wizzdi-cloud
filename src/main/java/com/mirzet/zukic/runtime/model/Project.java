package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Project extends Basic {

  @OneToMany(targetEntity = PersonToProject.class, mappedBy = "project")
  @JsonIgnore
  private List<PersonToProject> projectPersonToProjects;

  @OneToMany(targetEntity = Task.class, mappedBy = "project")
  @JsonIgnore
  private List<Task> projectTasks;

  private OffsetDateTime plannedStartDate;

  private OffsetDateTime plannedEndDate;

  private OffsetDateTime actualStartDate;

  private OffsetDateTime actualEndDate;

  @ManyToOne(targetEntity = Client.class)
  private Client client;

  /**
   * @return projectPersonToProjects
   */
  @OneToMany(targetEntity = PersonToProject.class, mappedBy = "project")
  @JsonIgnore
  public List<PersonToProject> getProjectPersonToProjects() {
    return this.projectPersonToProjects;
  }

  /**
   * @param projectPersonToProjects projectPersonToProjects to set
   * @return Project
   */
  public <T extends Project> T setProjectPersonToProjects(
      List<PersonToProject> projectPersonToProjects) {
    this.projectPersonToProjects = projectPersonToProjects;
    return (T) this;
  }

  /**
   * @return projectTasks
   */
  @OneToMany(targetEntity = Task.class, mappedBy = "project")
  @JsonIgnore
  public List<Task> getProjectTasks() {
    return this.projectTasks;
  }

  /**
   * @param projectTasks projectTasks to set
   * @return Project
   */
  public <T extends Project> T setProjectTasks(List<Task> projectTasks) {
    this.projectTasks = projectTasks;
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
   * @return Project
   */
  public <T extends Project> T setPlannedStartDate(OffsetDateTime plannedStartDate) {
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
   * @return Project
   */
  public <T extends Project> T setPlannedEndDate(OffsetDateTime plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
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
   * @return Project
   */
  public <T extends Project> T setActualStartDate(OffsetDateTime actualStartDate) {
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
   * @return Project
   */
  public <T extends Project> T setActualEndDate(OffsetDateTime actualEndDate) {
    this.actualEndDate = actualEndDate;
    return (T) this;
  }

  /**
   * @return client
   */
  @ManyToOne(targetEntity = Client.class)
  public Client getClient() {
    return this.client;
  }

  /**
   * @param client client to set
   * @return Project
   */
  public <T extends Project> T setClient(Client client) {
    this.client = client;
    return (T) this;
  }
}
