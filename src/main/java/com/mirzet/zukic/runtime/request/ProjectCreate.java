package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;
import java.time.OffsetDateTime;

/** Object Used to Create Project */
@IdValid.List({
  @IdValid(
      targetField = "client",
      field = "clientId",
      fieldType = Client.class,
      groups = {Update.class, Create.class})
})
public class ProjectCreate extends BasicCreate {

  private OffsetDateTime actualEndDate;

  private OffsetDateTime actualStartDate;

  @JsonIgnore private Client client;

  private String clientId;

  private OffsetDateTime plannedEndDate;

  private OffsetDateTime plannedStartDate;

  /**
   * @return actualEndDate
   */
  public OffsetDateTime getActualEndDate() {
    return this.actualEndDate;
  }

  /**
   * @param actualEndDate actualEndDate to set
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setActualEndDate(OffsetDateTime actualEndDate) {
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
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setActualStartDate(OffsetDateTime actualStartDate) {
    this.actualStartDate = actualStartDate;
    return (T) this;
  }

  /**
   * @return client
   */
  @JsonIgnore
  public Client getClient() {
    return this.client;
  }

  /**
   * @param client client to set
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setClient(Client client) {
    this.client = client;
    return (T) this;
  }

  /**
   * @return clientId
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * @param clientId clientId to set
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setClientId(String clientId) {
    this.clientId = clientId;
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
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setPlannedEndDate(OffsetDateTime plannedEndDate) {
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
   * @return ProjectCreate
   */
  public <T extends ProjectCreate> T setPlannedStartDate(OffsetDateTime plannedStartDate) {
    this.plannedStartDate = plannedStartDate;
    return (T) this;
  }
}
