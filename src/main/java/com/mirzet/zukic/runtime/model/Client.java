package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Client extends Basic {

  @OneToMany(targetEntity = Project.class, mappedBy = "client")
  @JsonIgnore
  private List<Project> clientProjects;

  /**
   * @return clientProjects
   */
  @OneToMany(targetEntity = Project.class, mappedBy = "client")
  @JsonIgnore
  public List<Project> getClientProjects() {
    return this.clientProjects;
  }

  /**
   * @param clientProjects clientProjects to set
   * @return Client
   */
  public <T extends Client> T setClientProjects(List<Project> clientProjects) {
    this.clientProjects = clientProjects;
    return (T) this;
  }
}
