package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Resource extends Basic {

  @OneToMany(targetEntity = Task.class, mappedBy = "resource")
  @JsonIgnore
  private List<Task> resourceTasks;

  /**
   * @return resourceTasks
   */
  @OneToMany(targetEntity = Task.class, mappedBy = "resource")
  @JsonIgnore
  public List<Task> getResourceTasks() {
    return this.resourceTasks;
  }

  /**
   * @param resourceTasks resourceTasks to set
   * @return Resource
   */
  public <T extends Resource> T setResourceTasks(List<Task> resourceTasks) {
    this.resourceTasks = resourceTasks;
    return (T) this;
  }
}
