package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Activity_;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask_;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Project_;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.Resource_;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.Task_;
import com.mirzet.zukic.runtime.request.TaskFilter;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param taskFilter Object Used to List Task
   * @param securityContext
   * @return List of Task
   */
  public List<Task> listAllTasks(TaskFilter taskFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Task> q = cb.createQuery(Task.class);
    Root<Task> r = q.from(Task.class);
    List<Predicate> preds = new ArrayList<>();
    addTaskPredicate(taskFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Task> query = em.createQuery(q);

    if (taskFilter.getPageSize() != null
        && taskFilter.getCurrentPage() != null
        && taskFilter.getPageSize() > 0
        && taskFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(taskFilter.getPageSize() * taskFilter.getCurrentPage())
          .setMaxResults(taskFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Task> void addTaskPredicate(
      TaskFilter taskFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(taskFilter, cb, q, r, preds, securityContext);

    if (taskFilter.getStoryPointsStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(r.get(Task_.storyPoints), taskFilter.getStoryPointsStart()));
    }
    if (taskFilter.getStoryPointsEnd() != null) {
      preds.add(cb.lessThanOrEqualTo(r.get(Task_.storyPoints), taskFilter.getStoryPointsEnd()));
    }

    if (taskFilter.getTaskDependendTaskToMainTaskses() != null
        && !taskFilter.getTaskDependendTaskToMainTaskses().isEmpty()) {
      Set<String> ids =
          taskFilter.getTaskDependendTaskToMainTaskses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, DependendTaskToMainTask> join = r.join(Task_.taskDependendTaskToMainTasks);
      preds.add(join.get(DependendTaskToMainTask_.id).in(ids));
    }

    if (taskFilter.getResources() != null && !taskFilter.getResources().isEmpty()) {
      Set<String> ids =
          taskFilter.getResources().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Resource> join = r.join(Task_.resource);
      preds.add(join.get(Resource_.id).in(ids));
    }

    if (taskFilter.getProjects() != null && !taskFilter.getProjects().isEmpty()) {
      Set<String> ids =
          taskFilter.getProjects().parallelStream().map(f -> f.getId()).collect(Collectors.toSet());
      Join<T, Project> join = r.join(Task_.project);
      preds.add(join.get(Project_.id).in(ids));
    }

    if (taskFilter.getTaskActivitieses() != null && !taskFilter.getTaskActivitieses().isEmpty()) {
      Set<String> ids =
          taskFilter.getTaskActivitieses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Activity> join = r.join(Task_.taskActivities);
      preds.add(join.get(Activity_.id).in(ids));
    }

    if (taskFilter.getTaskStatus() != null && !taskFilter.getTaskStatus().isEmpty()) {
      preds.add(r.get(Task_.taskStatus).in(taskFilter.getTaskStatus()));
    }
  }

  /**
   * @param taskFilter Object Used to List Task
   * @param securityContext
   * @return count of Task
   */
  public Long countAllTasks(TaskFilter taskFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Task> r = q.from(Task.class);
    List<Predicate> preds = new ArrayList<>();
    addTaskPredicate(taskFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> q = cb.createQuery(c);
    Root<T> r = q.from(c);
    q.select(r).where(r.get(idField).in(ids));
    return em.createQuery(q).getResultList();
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return listByIds(c, idField, Collections.singleton(id)).stream().findFirst().orElse(null);
  }

  @Transactional
  public void merge(java.lang.Object base) {
    em.merge(base);
    applicationEventPublisher.publishEvent(base);
  }

  @Transactional
  public void massMerge(List<?> toMerge) {
    for (Object o : toMerge) {
      em.merge(o);
      applicationEventPublisher.publishEvent(o);
    }
  }
}
