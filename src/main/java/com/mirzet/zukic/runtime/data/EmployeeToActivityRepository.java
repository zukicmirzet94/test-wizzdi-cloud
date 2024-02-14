package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Activity_;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.EmployeeToActivity_;
import com.mirzet.zukic.runtime.model.Employee_;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.model.Role_;
import com.mirzet.zukic.runtime.request.EmployeeToActivityFilter;
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
public class EmployeeToActivityRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param employeeToActivityFilter Object Used to List EmployeeToActivity
   * @param securityContext
   * @return List of EmployeeToActivity
   */
  public List<EmployeeToActivity> listAllEmployeeToActivities(
      EmployeeToActivityFilter employeeToActivityFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<EmployeeToActivity> q = cb.createQuery(EmployeeToActivity.class);
    Root<EmployeeToActivity> r = q.from(EmployeeToActivity.class);
    List<Predicate> preds = new ArrayList<>();
    addEmployeeToActivityPredicate(employeeToActivityFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<EmployeeToActivity> query = em.createQuery(q);

    if (employeeToActivityFilter.getPageSize() != null
        && employeeToActivityFilter.getCurrentPage() != null
        && employeeToActivityFilter.getPageSize() > 0
        && employeeToActivityFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              employeeToActivityFilter.getPageSize() * employeeToActivityFilter.getCurrentPage())
          .setMaxResults(employeeToActivityFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends EmployeeToActivity> void addEmployeeToActivityPredicate(
      EmployeeToActivityFilter employeeToActivityFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (employeeToActivityFilter.getRoles() != null
        && !employeeToActivityFilter.getRoles().isEmpty()) {
      Set<String> ids =
          employeeToActivityFilter.getRoles().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Role> join = r.join(EmployeeToActivity_.role);
      preds.add(join.get(Role_.id).in(ids));
    }

    if (employeeToActivityFilter.getActivities() != null
        && !employeeToActivityFilter.getActivities().isEmpty()) {
      Set<String> ids =
          employeeToActivityFilter.getActivities().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Activity> join = r.join(EmployeeToActivity_.activity);
      preds.add(join.get(Activity_.id).in(ids));
    }

    if (employeeToActivityFilter.getEmployees() != null
        && !employeeToActivityFilter.getEmployees().isEmpty()) {
      Set<String> ids =
          employeeToActivityFilter.getEmployees().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Employee> join = r.join(EmployeeToActivity_.employee);
      preds.add(join.get(Employee_.id).in(ids));
    }

    if (employeeToActivityFilter.getId() != null && !employeeToActivityFilter.getId().isEmpty()) {
      preds.add(r.get(EmployeeToActivity_.id).in(employeeToActivityFilter.getId()));
    }
  }

  /**
   * @param employeeToActivityFilter Object Used to List EmployeeToActivity
   * @param securityContext
   * @return count of EmployeeToActivity
   */
  public Long countAllEmployeeToActivities(
      EmployeeToActivityFilter employeeToActivityFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<EmployeeToActivity> r = q.from(EmployeeToActivity.class);
    List<Predicate> preds = new ArrayList<>();
    addEmployeeToActivityPredicate(employeeToActivityFilter, cb, q, r, preds, securityContext);
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
