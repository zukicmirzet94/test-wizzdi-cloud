package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.DependendTaskToMainTaskRepository;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskCreate;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskFilter;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependendTaskToMainTaskService {

  @Autowired private DependendTaskToMainTaskRepository repository;

  /**
   * @param dependendTaskToMainTaskCreate Object Used to Create DependendTaskToMainTask
   * @param securityContext
   * @return created DependendTaskToMainTask
   */
  public DependendTaskToMainTask createDependendTaskToMainTask(
      DependendTaskToMainTaskCreate dependendTaskToMainTaskCreate,
      UserSecurityContext securityContext) {
    DependendTaskToMainTask dependendTaskToMainTask =
        createDependendTaskToMainTaskNoMerge(dependendTaskToMainTaskCreate, securityContext);
    this.repository.merge(dependendTaskToMainTask);
    return dependendTaskToMainTask;
  }

  /**
   * @param dependendTaskToMainTaskCreate Object Used to Create DependendTaskToMainTask
   * @param securityContext
   * @return created DependendTaskToMainTask unmerged
   */
  public DependendTaskToMainTask createDependendTaskToMainTaskNoMerge(
      DependendTaskToMainTaskCreate dependendTaskToMainTaskCreate,
      UserSecurityContext securityContext) {
    DependendTaskToMainTask dependendTaskToMainTask = new DependendTaskToMainTask();
    dependendTaskToMainTask.setId(UUID.randomUUID().toString());
    updateDependendTaskToMainTaskNoMerge(dependendTaskToMainTask, dependendTaskToMainTaskCreate);

    return dependendTaskToMainTask;
  }

  /**
   * @param dependendTaskToMainTaskCreate Object Used to Create DependendTaskToMainTask
   * @param dependendTaskToMainTask
   * @return if dependendTaskToMainTask was updated
   */
  public boolean updateDependendTaskToMainTaskNoMerge(
      DependendTaskToMainTask dependendTaskToMainTask,
      DependendTaskToMainTaskCreate dependendTaskToMainTaskCreate) {
    boolean update = false;

    if (dependendTaskToMainTaskCreate.getDependTasks() != null
        && (dependendTaskToMainTask.getDependTasks() == null
            || !dependendTaskToMainTaskCreate
                .getDependTasks()
                .getId()
                .equals(dependendTaskToMainTask.getDependTasks().getId()))) {
      dependendTaskToMainTask.setDependTasks(dependendTaskToMainTaskCreate.getDependTasks());
      update = true;
    }

    if (dependendTaskToMainTaskCreate.getTask() != null
        && (dependendTaskToMainTask.getTask() == null
            || !dependendTaskToMainTaskCreate
                .getTask()
                .getId()
                .equals(dependendTaskToMainTask.getTask().getId()))) {
      dependendTaskToMainTask.setTask(dependendTaskToMainTaskCreate.getTask());
      update = true;
    }

    return update;
  }

  /**
   * @param dependendTaskToMainTaskUpdate
   * @param securityContext
   * @return dependendTaskToMainTask
   */
  public DependendTaskToMainTask updateDependendTaskToMainTask(
      DependendTaskToMainTaskUpdate dependendTaskToMainTaskUpdate,
      UserSecurityContext securityContext) {
    DependendTaskToMainTask dependendTaskToMainTask =
        dependendTaskToMainTaskUpdate.getDependendTaskToMainTask();
    if (updateDependendTaskToMainTaskNoMerge(
        dependendTaskToMainTask, dependendTaskToMainTaskUpdate)) {
      this.repository.merge(dependendTaskToMainTask);
    }
    return dependendTaskToMainTask;
  }

  /**
   * @param dependendTaskToMainTaskFilter Object Used to List DependendTaskToMainTask
   * @param securityContext
   * @return PaginationResponse<DependendTaskToMainTask> containing paging information for
   *     DependendTaskToMainTask
   */
  public PaginationResponse<DependendTaskToMainTask> getAllDependendTaskToMainTasks(
      DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      UserSecurityContext securityContext) {
    List<DependendTaskToMainTask> list =
        listAllDependendTaskToMainTasks(dependendTaskToMainTaskFilter, securityContext);
    long count =
        this.repository.countAllDependendTaskToMainTasks(
            dependendTaskToMainTaskFilter, securityContext);
    return new PaginationResponse<>(list, dependendTaskToMainTaskFilter.getPageSize(), count);
  }

  /**
   * @param dependendTaskToMainTaskFilter Object Used to List DependendTaskToMainTask
   * @param securityContext
   * @return List of DependendTaskToMainTask
   */
  public List<DependendTaskToMainTask> listAllDependendTaskToMainTasks(
      DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      UserSecurityContext securityContext) {
    return this.repository.listAllDependendTaskToMainTasks(
        dependendTaskToMainTaskFilter, securityContext);
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public void merge(java.lang.Object base) {
    this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}
