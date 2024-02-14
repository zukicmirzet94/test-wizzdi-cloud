package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Client */
@IdValid.List({
  @IdValid(
      targetField = "client",
      field = "id",
      fieldType = Client.class,
      groups = {Update.class})
})
public class ClientUpdate extends ClientCreate {

  @JsonIgnore private Client client;

  private String id;

  /**
   * @return client
   */
  @JsonIgnore
  public Client getClient() {
    return this.client;
  }

  /**
   * @param client client to set
   * @return ClientUpdate
   */
  public <T extends ClientUpdate> T setClient(Client client) {
    this.client = client;
    return (T) this;
  }

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return ClientUpdate
   */
  public <T extends ClientUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
