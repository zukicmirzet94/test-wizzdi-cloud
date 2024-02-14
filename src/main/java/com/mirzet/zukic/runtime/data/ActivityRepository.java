package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Activity_;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.EmployeeToActivity_;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.Task_;
import com.mirzet.zukic.runtime.request.ActivityFilter;
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
public class ActivityRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param activityFilter Object Used to List Activity
   * @param securityContext
   * @return List of Activity
   */
  public List<Activity> listAllActivities(
      ActivityFilter activityFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Activity> q = cb.createQuery(Activity.class);
    Root<Activity> r = q.from(Activity.class);
    List<Predicate> preds = new ArrayList<>();
    addActivityPredicate(activityFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Activity> query = em.createQuery(q);

    if (activityFilter.getPageSize() != null
        && activityFilter.getCurrentPage() != null
        && activityFilter.getPageSize() > 0
        && activityFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(activityFilter.getPageSize() * activityFilter.getCurrentPage())
          .setMaxResults(activityFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Activity> void addActivityPredicate(
      ActivityFilter activityFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(activityFilter, cb, q, r, preds, securityContext);

    if (activityFilter.getActualEndDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.actualEndDate), activityFilter.getActualEndDateStart()));
    }
    if (activityFilter.getActualEndDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Activity_.actualEndDate), activityFilter.getActualEndDateEnd()));
    }

    if (activityFilter.getPriority() != null && !activityFilter.getPriority().isEmpty()) {
      preds.add(r.get(Activity_.priority).in(activityFilter.getPriority()));
    }

    if (activityFilter.getPlannedEndDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.plannedEndDate), activityFilter.getPlannedEndDateStart()));
    }
    if (activityFilter.getPlannedEndDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Activity_.plannedEndDate), activityFilter.getPlannedEndDateEnd()));
    }

    if (activityFilter.getActualStartDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.actualStartDate), activityFilter.getActualStartDateStart()));
    }
    if (activityFilter.getActualStartDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Activity_.actualStartDate), activityFilter.getActualStartDateEnd()));
    }

    if (activityFilter.getTasks() != null && !activityFilter.getTasks().isEmpty()) {
      Set<String> ids =
          activityFilter.getTasks().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Task> join = r.join(Activity_.task);
      preds.add(join.get(Task_.id).in(ids));
    }

    if (activityFilter.getPlannedBudgetStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.plannedBudget), activityFilter.getPlannedBudgetStart()));
    }
    if (activityFilter.getPlannedBudgetEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Activity_.plannedBudget), activityFilter.getPlannedBudgetEnd()));
    }

    if (activityFilter.getActivityEmployeeToActivitieses() != null
        && !activityFilter.getActivityEmployeeToActivitieses().isEmpty()) {
      Set<String> ids =
          activityFilter.getActivityEmployeeToActivitieses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, EmployeeToActivity> join = r.join(Activity_.activityEmployeeToActivities);
      preds.add(join.get(EmployeeToActivity_.id).in(ids));
    }

    if (activityFilter.getPlannedStartDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.plannedStartDate), activityFilter.getPlannedStartDateStart()));
    }
    if (activityFilter.getPlannedStartDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Activity_.plannedStartDate), activityFilter.getPlannedStartDateEnd()));
    }

    if (activityFilter.getActualBudgetStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Activity_.actualBudget), activityFilter.getActualBudgetStart()));
    }
    if (activityFilter.getActualBudgetEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(r.get(Activity_.actualBudget), activityFilter.getActualBudgetEnd()));
    }
  }

  /**
   * @param activityFilter Object Used to List Activity
   * @param securityContext
   * @return count of Activity
   */
  public Long countAllActivities(
      ActivityFilter activityFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Activity> r = q.from(Activity.class);
    List<Predicate> preds = new ArrayList<>();
    addActivityPredicate(activityFilter, cb, q, r, preds, securityContext);
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
