package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.DependTasks_;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask_;
import com.mirzet.zukic.runtime.request.DependTasksFilter;
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
public class DependTasksRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private TaskRepository taskRepository;

  /**
   * @param dependTasksFilter Object Used to List DependTasks
   * @param securityContext
   * @return List of DependTasks
   */
  public List<DependTasks> listAllDependTaskses(
      DependTasksFilter dependTasksFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<DependTasks> q = cb.createQuery(DependTasks.class);
    Root<DependTasks> r = q.from(DependTasks.class);
    List<Predicate> preds = new ArrayList<>();
    addDependTasksPredicate(dependTasksFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<DependTasks> query = em.createQuery(q);

    if (dependTasksFilter.getPageSize() != null
        && dependTasksFilter.getCurrentPage() != null
        && dependTasksFilter.getPageSize() > 0
        && dependTasksFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(dependTasksFilter.getPageSize() * dependTasksFilter.getCurrentPage())
          .setMaxResults(dependTasksFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends DependTasks> void addDependTasksPredicate(
      DependTasksFilter dependTasksFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    taskRepository.addTaskPredicate(dependTasksFilter, cb, q, r, preds, securityContext);

    if (dependTasksFilter.getDependTasksDependendTaskToMainTaskses() != null
        && !dependTasksFilter.getDependTasksDependendTaskToMainTaskses().isEmpty()) {
      Set<String> ids =
          dependTasksFilter.getDependTasksDependendTaskToMainTaskses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, DependendTaskToMainTask> join =
          r.join(DependTasks_.dependTasksDependendTaskToMainTasks);
      preds.add(join.get(DependendTaskToMainTask_.id).in(ids));
    }
  }

  /**
   * @param dependTasksFilter Object Used to List DependTasks
   * @param securityContext
   * @return count of DependTasks
   */
  public Long countAllDependTaskses(
      DependTasksFilter dependTasksFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<DependTasks> r = q.from(DependTasks.class);
    List<Predicate> preds = new ArrayList<>();
    addDependTasksPredicate(dependTasksFilter, cb, q, r, preds, securityContext);
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
