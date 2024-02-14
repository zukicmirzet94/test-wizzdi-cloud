package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

/** Object Used to List Project */
@IdValid.List({
  @IdValid(
      targetField = "projectPersonToProjectses",
      field = "projectPersonToProjectsIds",
      fieldType = PersonToProject.class),
  @IdValid(targetField = "projectTaskses", field = "projectTasksIds", fieldType = Task.class),
  @IdValid(targetField = "clients", field = "clientIds", fieldType = Client.class)
})
public class ProjectFilter extends BasicFilter {

  private OffsetDateTime actualEndDateEnd;

  private OffsetDateTime actualEndDateStart;

  private OffsetDateTime actualStartDateEnd;

  private OffsetDateTime actualStartDateStart;

  private Set<String> clientIds;

  @JsonIgnore private List<Client> clients;

  private OffsetDateTime plannedEndDateEnd;

  private OffsetDateTime plannedEndDateStart;

  private OffsetDateTime plannedStartDateEnd;

  private OffsetDateTime plannedStartDateStart;

  private Set<String> projectPersonToProjectsIds;

  @JsonIgnore private List<PersonToProject> projectPersonToProjectses;

  private Set<String> projectTasksIds;

  @JsonIgnore private List<Task> projectTaskses;

  /**
   * @return actualEndDateEnd
   */
  public OffsetDateTime getActualEndDateEnd() {
    return this.actualEndDateEnd;
  }

  /**
   * @param actualEndDateEnd actualEndDateEnd to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setActualEndDateEnd(OffsetDateTime actualEndDateEnd) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setActualEndDateStart(OffsetDateTime actualEndDateStart) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setActualStartDateEnd(OffsetDateTime actualStartDateEnd) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setActualStartDateStart(OffsetDateTime actualStartDateStart) {
    this.actualStartDateStart = actualStartDateStart;
    return (T) this;
  }

  /**
   * @return clientIds
   */
  public Set<String> getClientIds() {
    return this.clientIds;
  }

  /**
   * @param clientIds clientIds to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setClientIds(Set<String> clientIds) {
    this.clientIds = clientIds;
    return (T) this;
  }

  /**
   * @return clients
   */
  @JsonIgnore
  public List<Client> getClients() {
    return this.clients;
  }

  /**
   * @param clients clients to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setClients(List<Client> clients) {
    this.clients = clients;
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setPlannedEndDateEnd(OffsetDateTime plannedEndDateEnd) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setPlannedEndDateStart(OffsetDateTime plannedEndDateStart) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setPlannedStartDateEnd(OffsetDateTime plannedStartDateEnd) {
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
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setPlannedStartDateStart(
      OffsetDateTime plannedStartDateStart) {
    this.plannedStartDateStart = plannedStartDateStart;
    return (T) this;
  }

  /**
   * @return projectPersonToProjectsIds
   */
  public Set<String> getProjectPersonToProjectsIds() {
    return this.projectPersonToProjectsIds;
  }

  /**
   * @param projectPersonToProjectsIds projectPersonToProjectsIds to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setProjectPersonToProjectsIds(
      Set<String> projectPersonToProjectsIds) {
    this.projectPersonToProjectsIds = projectPersonToProjectsIds;
    return (T) this;
  }

  /**
   * @return projectPersonToProjectses
   */
  @JsonIgnore
  public List<PersonToProject> getProjectPersonToProjectses() {
    return this.projectPersonToProjectses;
  }

  /**
   * @param projectPersonToProjectses projectPersonToProjectses to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setProjectPersonToProjectses(
      List<PersonToProject> projectPersonToProjectses) {
    this.projectPersonToProjectses = projectPersonToProjectses;
    return (T) this;
  }

  /**
   * @return projectTasksIds
   */
  public Set<String> getProjectTasksIds() {
    return this.projectTasksIds;
  }

  /**
   * @param projectTasksIds projectTasksIds to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setProjectTasksIds(Set<String> projectTasksIds) {
    this.projectTasksIds = projectTasksIds;
    return (T) this;
  }

  /**
   * @return projectTaskses
   */
  @JsonIgnore
  public List<Task> getProjectTaskses() {
    return this.projectTaskses;
  }

  /**
   * @param projectTaskses projectTaskses to set
   * @return ProjectFilter
   */
  public <T extends ProjectFilter> T setProjectTaskses(List<Task> projectTaskses) {
    this.projectTaskses = projectTaskses;
    return (T) this;
  }
}
