package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Resource */
@IdValid.List({
  @IdValid(targetField = "resourceTaskses", field = "resourceTasksIds", fieldType = Task.class)
})
public class ResourceFilter extends BasicFilter {

  private Set<String> resourceTasksIds;

  @JsonIgnore private List<Task> resourceTaskses;

  /**
   * @return resourceTasksIds
   */
  public Set<String> getResourceTasksIds() {
    return this.resourceTasksIds;
  }

  /**
   * @param resourceTasksIds resourceTasksIds to set
   * @return ResourceFilter
   */
  public <T extends ResourceFilter> T setResourceTasksIds(Set<String> resourceTasksIds) {
    this.resourceTasksIds = resourceTasksIds;
    return (T) this;
  }

  /**
   * @return resourceTaskses
   */
  @JsonIgnore
  public List<Task> getResourceTaskses() {
    return this.resourceTaskses;
  }

  /**
   * @param resourceTaskses resourceTaskses to set
   * @return ResourceFilter
   */
  public <T extends ResourceFilter> T setResourceTaskses(List<Task> resourceTaskses) {
    this.resourceTaskses = resourceTaskses;
    return (T) this;
  }
}
