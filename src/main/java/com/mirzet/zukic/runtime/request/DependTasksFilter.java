package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List DependTasks */
@IdValid.List({
  @IdValid(
      targetField = "dependTasksDependendTaskToMainTaskses",
      field = "dependTasksDependendTaskToMainTasksIds",
      fieldType = DependendTaskToMainTask.class)
})
public class DependTasksFilter extends TaskFilter {

  private Set<String> dependTasksDependendTaskToMainTasksIds;

  @JsonIgnore private List<DependendTaskToMainTask> dependTasksDependendTaskToMainTaskses;

  /**
   * @return dependTasksDependendTaskToMainTasksIds
   */
  public Set<String> getDependTasksDependendTaskToMainTasksIds() {
    return this.dependTasksDependendTaskToMainTasksIds;
  }

  /**
   * @param dependTasksDependendTaskToMainTasksIds dependTasksDependendTaskToMainTasksIds to set
   * @return DependTasksFilter
   */
  public <T extends DependTasksFilter> T setDependTasksDependendTaskToMainTasksIds(
      Set<String> dependTasksDependendTaskToMainTasksIds) {
    this.dependTasksDependendTaskToMainTasksIds = dependTasksDependendTaskToMainTasksIds;
    return (T) this;
  }

  /**
   * @return dependTasksDependendTaskToMainTaskses
   */
  @JsonIgnore
  public List<DependendTaskToMainTask> getDependTasksDependendTaskToMainTaskses() {
    return this.dependTasksDependendTaskToMainTaskses;
  }

  /**
   * @param dependTasksDependendTaskToMainTaskses dependTasksDependendTaskToMainTaskses to set
   * @return DependTasksFilter
   */
  public <T extends DependTasksFilter> T setDependTasksDependendTaskToMainTaskses(
      List<DependendTaskToMainTask> dependTasksDependendTaskToMainTaskses) {
    this.dependTasksDependendTaskToMainTaskses = dependTasksDependendTaskToMainTaskses;
    return (T) this;
  }
}
