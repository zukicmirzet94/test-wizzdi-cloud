package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.DependTasks_;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask_;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.Task_;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskFilter;
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
public class DependendTaskToMainTaskRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param dependendTaskToMainTaskFilter Object Used to List DependendTaskToMainTask
   * @param securityContext
   * @return List of DependendTaskToMainTask
   */
  public List<DependendTaskToMainTask> listAllDependendTaskToMainTasks(
      DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<DependendTaskToMainTask> q = cb.createQuery(DependendTaskToMainTask.class);
    Root<DependendTaskToMainTask> r = q.from(DependendTaskToMainTask.class);
    List<Predicate> preds = new ArrayList<>();
    addDependendTaskToMainTaskPredicate(
        dependendTaskToMainTaskFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<DependendTaskToMainTask> query = em.createQuery(q);

    if (dependendTaskToMainTaskFilter.getPageSize() != null
        && dependendTaskToMainTaskFilter.getCurrentPage() != null
        && dependendTaskToMainTaskFilter.getPageSize() > 0
        && dependendTaskToMainTaskFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              dependendTaskToMainTaskFilter.getPageSize()
                  * dependendTaskToMainTaskFilter.getCurrentPage())
          .setMaxResults(dependendTaskToMainTaskFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends DependendTaskToMainTask> void addDependendTaskToMainTaskPredicate(
      DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (dependendTaskToMainTaskFilter.getDependTaskses() != null
        && !dependendTaskToMainTaskFilter.getDependTaskses().isEmpty()) {
      Set<String> ids =
          dependendTaskToMainTaskFilter.getDependTaskses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, DependTasks> join = r.join(DependendTaskToMainTask_.dependTasks);
      preds.add(join.get(DependTasks_.id).in(ids));
    }

    if (dependendTaskToMainTaskFilter.getId() != null
        && !dependendTaskToMainTaskFilter.getId().isEmpty()) {
      preds.add(r.get(DependendTaskToMainTask_.id).in(dependendTaskToMainTaskFilter.getId()));
    }

    if (dependendTaskToMainTaskFilter.getTasks() != null
        && !dependendTaskToMainTaskFilter.getTasks().isEmpty()) {
      Set<String> ids =
          dependendTaskToMainTaskFilter.getTasks().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Task> join = r.join(DependendTaskToMainTask_.task);
      preds.add(join.get(Task_.id).in(ids));
    }
  }

  /**
   * @param dependendTaskToMainTaskFilter Object Used to List DependendTaskToMainTask
   * @param securityContext
   * @return count of DependendTaskToMainTask
   */
  public Long countAllDependendTaskToMainTasks(
      DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<DependendTaskToMainTask> r = q.from(DependendTaskToMainTask.class);
    List<Predicate> preds = new ArrayList<>();
    addDependendTaskToMainTaskPredicate(
        dependendTaskToMainTaskFilter, cb, q, r, preds, securityContext);
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
