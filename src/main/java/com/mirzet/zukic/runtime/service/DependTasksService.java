package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.DependTasksRepository;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.request.DependTasksCreate;
import com.mirzet.zukic.runtime.request.DependTasksFilter;
import com.mirzet.zukic.runtime.request.DependTasksUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependTasksService {

  @Autowired private DependTasksRepository repository;

  @Autowired private TaskService taskService;

  /**
   * @param dependTasksCreate Object Used to Create DependTasks
   * @param securityContext
   * @return created DependTasks
   */
  public DependTasks createDependTasks(
      DependTasksCreate dependTasksCreate, UserSecurityContext securityContext) {
    DependTasks dependTasks = createDependTasksNoMerge(dependTasksCreate, securityContext);
    this.repository.merge(dependTasks);
    return dependTasks;
  }

  /**
   * @param dependTasksCreate Object Used to Create DependTasks
   * @param securityContext
   * @return created DependTasks unmerged
   */
  public DependTasks createDependTasksNoMerge(
      DependTasksCreate dependTasksCreate, UserSecurityContext securityContext) {
    DependTasks dependTasks = new DependTasks();
    dependTasks.setId(UUID.randomUUID().toString());
    updateDependTasksNoMerge(dependTasks, dependTasksCreate);

    return dependTasks;
  }

  /**
   * @param dependTasksCreate Object Used to Create DependTasks
   * @param dependTasks
   * @return if dependTasks was updated
   */
  public boolean updateDependTasksNoMerge(
      DependTasks dependTasks, DependTasksCreate dependTasksCreate) {
    boolean update = taskService.updateTaskNoMerge(dependTasks, dependTasksCreate);

    return update;
  }

  /**
   * @param dependTasksUpdate
   * @param securityContext
   * @return dependTasks
   */
  public DependTasks updateDependTasks(
      DependTasksUpdate dependTasksUpdate, UserSecurityContext securityContext) {
    DependTasks dependTasks = dependTasksUpdate.getDependTasks();
    if (updateDependTasksNoMerge(dependTasks, dependTasksUpdate)) {
      this.repository.merge(dependTasks);
    }
    return dependTasks;
  }

  /**
   * @param dependTasksFilter Object Used to List DependTasks
   * @param securityContext
   * @return PaginationResponse<DependTasks> containing paging information for DependTasks
   */
  public PaginationResponse<DependTasks> getAllDependTaskses(
      DependTasksFilter dependTasksFilter, UserSecurityContext securityContext) {
    List<DependTasks> list = listAllDependTaskses(dependTasksFilter, securityContext);
    long count = this.repository.countAllDependTaskses(dependTasksFilter, securityContext);
    return new PaginationResponse<>(list, dependTasksFilter.getPageSize(), count);
  }

  /**
   * @param dependTasksFilter Object Used to List DependTasks
   * @param securityContext
   * @return List of DependTasks
   */
  public List<DependTasks> listAllDependTaskses(
      DependTasksFilter dependTasksFilter, UserSecurityContext securityContext) {
    return this.repository.listAllDependTaskses(dependTasksFilter, securityContext);
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
