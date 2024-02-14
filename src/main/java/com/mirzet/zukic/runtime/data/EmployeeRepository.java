package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.EmployeeToActivity_;
import com.mirzet.zukic.runtime.model.Employee_;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.Organization_;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.model.Qualification_;
import com.mirzet.zukic.runtime.request.EmployeeFilter;
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
public class EmployeeRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private PersonRepository personRepository;

  /**
   * @param employeeFilter Object Used to List Employee
   * @param securityContext
   * @return List of Employee
   */
  public List<Employee> listAllEmployees(
      EmployeeFilter employeeFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
    Root<Employee> r = q.from(Employee.class);
    List<Predicate> preds = new ArrayList<>();
    addEmployeePredicate(employeeFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Employee> query = em.createQuery(q);

    if (employeeFilter.getPageSize() != null
        && employeeFilter.getCurrentPage() != null
        && employeeFilter.getPageSize() > 0
        && employeeFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(employeeFilter.getPageSize() * employeeFilter.getCurrentPage())
          .setMaxResults(employeeFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Employee> void addEmployeePredicate(
      EmployeeFilter employeeFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    personRepository.addPersonPredicate(employeeFilter, cb, q, r, preds, securityContext);

    if (employeeFilter.getEmployeeEmployeeToActivitieses() != null
        && !employeeFilter.getEmployeeEmployeeToActivitieses().isEmpty()) {
      Set<String> ids =
          employeeFilter.getEmployeeEmployeeToActivitieses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, EmployeeToActivity> join = r.join(Employee_.employeeEmployeeToActivities);
      preds.add(join.get(EmployeeToActivity_.id).in(ids));
    }

    if (employeeFilter.getOrganizations() != null && !employeeFilter.getOrganizations().isEmpty()) {
      Set<String> ids =
          employeeFilter.getOrganizations().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Organization> join = r.join(Employee_.organization);
      preds.add(join.get(Organization_.id).in(ids));
    }

    if (employeeFilter.getQualifications() != null
        && !employeeFilter.getQualifications().isEmpty()) {
      Set<String> ids =
          employeeFilter.getQualifications().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Qualification> join = r.join(Employee_.qualification);
      preds.add(join.get(Qualification_.id).in(ids));
    }
  }

  /**
   * @param employeeFilter Object Used to List Employee
   * @param securityContext
   * @return count of Employee
   */
  public Long countAllEmployees(
      EmployeeFilter employeeFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Employee> r = q.from(Employee.class);
    List<Predicate> preds = new ArrayList<>();
    addEmployeePredicate(employeeFilter, cb, q, r, preds, securityContext);
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
