package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Client */
@IdValid.List({
  @IdValid(targetField = "clientProjectses", field = "clientProjectsIds", fieldType = Project.class)
})
public class ClientFilter extends BasicFilter {

  private Set<String> clientProjectsIds;

  @JsonIgnore private List<Project> clientProjectses;

  /**
   * @return clientProjectsIds
   */
  public Set<String> getClientProjectsIds() {
    return this.clientProjectsIds;
  }

  /**
   * @param clientProjectsIds clientProjectsIds to set
   * @return ClientFilter
   */
  public <T extends ClientFilter> T setClientProjectsIds(Set<String> clientProjectsIds) {
    this.clientProjectsIds = clientProjectsIds;
    return (T) this;
  }

  /**
   * @return clientProjectses
   */
  @JsonIgnore
  public List<Project> getClientProjectses() {
    return this.clientProjectses;
  }

  /**
   * @param clientProjectses clientProjectses to set
   * @return ClientFilter
   */
  public <T extends ClientFilter> T setClientProjectses(List<Project> clientProjectses) {
    this.clientProjectses = clientProjectses;
    return (T) this;
  }
}
