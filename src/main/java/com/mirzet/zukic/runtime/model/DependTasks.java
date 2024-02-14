package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class DependTasks extends Task {

  @OneToMany(targetEntity = DependendTaskToMainTask.class, mappedBy = "dependTasks")
  @JsonIgnore
  private List<DependendTaskToMainTask> dependTasksDependendTaskToMainTasks;

  /**
   * @return dependTasksDependendTaskToMainTasks
   */
  @OneToMany(targetEntity = DependendTaskToMainTask.class, mappedBy = "dependTasks")
  @JsonIgnore
  public List<DependendTaskToMainTask> getDependTasksDependendTaskToMainTasks() {
    return this.dependTasksDependendTaskToMainTasks;
  }

  /**
   * @param dependTasksDependendTaskToMainTasks dependTasksDependendTaskToMainTasks to set
   * @return DependTasks
   */
  public <T extends DependTasks> T setDependTasksDependendTaskToMainTasks(
      List<DependendTaskToMainTask> dependTasksDependendTaskToMainTasks) {
    this.dependTasksDependendTaskToMainTasks = dependTasksDependendTaskToMainTasks;
    return (T) this;
  }
}
