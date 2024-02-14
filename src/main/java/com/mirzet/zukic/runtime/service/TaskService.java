package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.TaskRepository;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.request.TaskCreate;
import com.mirzet.zukic.runtime.request.TaskFilter;
import com.mirzet.zukic.runtime.request.TaskUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskService {

  @Autowired private TaskRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param taskCreate Object Used to Create Task
   * @param securityContext
   * @return created Task
   */
  public Task createTask(TaskCreate taskCreate, UserSecurityContext securityContext) {
    Task task = createTaskNoMerge(taskCreate, securityContext);
    this.repository.merge(task);
    return task;
  }

  /**
   * @param taskCreate Object Used to Create Task
   * @param securityContext
   * @return created Task unmerged
   */
  public Task createTaskNoMerge(TaskCreate taskCreate, UserSecurityContext securityContext) {
    Task task = new Task();
    task.setId(UUID.randomUUID().toString());
    updateTaskNoMerge(task, taskCreate);

    return task;
  }

  /**
   * @param taskCreate Object Used to Create Task
   * @param task
   * @return if task was updated
   */
  public boolean updateTaskNoMerge(Task task, TaskCreate taskCreate) {
    boolean update = basicService.updateBasicNoMerge(task, taskCreate);

    if (taskCreate.getStoryPoints() != null
        && (!taskCreate.getStoryPoints().equals(task.getStoryPoints()))) {
      task.setStoryPoints(taskCreate.getStoryPoints());
      update = true;
    }

    if (taskCreate.getResource() != null
        && (task.getResource() == null
            || !taskCreate.getResource().getId().equals(task.getResource().getId()))) {
      task.setResource(taskCreate.getResource());
      update = true;
    }

    if (taskCreate.getProject() != null
        && (task.getProject() == null
            || !taskCreate.getProject().getId().equals(task.getProject().getId()))) {
      task.setProject(taskCreate.getProject());
      update = true;
    }

    if (taskCreate.getTaskStatus() != null
        && (!taskCreate.getTaskStatus().equals(task.getTaskStatus()))) {
      task.setTaskStatus(taskCreate.getTaskStatus());
      update = true;
    }

    return update;
  }

  /**
   * @param taskUpdate
   * @param securityContext
   * @return task
   */
  public Task updateTask(TaskUpdate taskUpdate, UserSecurityContext securityContext) {
    Task task = taskUpdate.getTask();
    if (updateTaskNoMerge(task, taskUpdate)) {
      this.repository.merge(task);
    }
    return task;
  }

  /**
   * @param taskFilter Object Used to List Task
   * @param securityContext
   * @return PaginationResponse<Task> containing paging information for Task
   */
  public PaginationResponse<Task> getAllTasks(
      TaskFilter taskFilter, UserSecurityContext securityContext) {
    List<Task> list = listAllTasks(taskFilter, securityContext);
    long count = this.repository.countAllTasks(taskFilter, securityContext);
    return new PaginationResponse<>(list, taskFilter.getPageSize(), count);
  }

  /**
   * @param taskFilter Object Used to List Task
   * @param securityContext
   * @return List of Task
   */
  public List<Task> listAllTasks(TaskFilter taskFilter, UserSecurityContext securityContext) {
    return this.repository.listAllTasks(taskFilter, securityContext);
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
