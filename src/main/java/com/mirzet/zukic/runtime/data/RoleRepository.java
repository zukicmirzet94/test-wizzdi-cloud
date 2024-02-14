package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.EmployeeToActivity_;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.model.Role_;
import com.mirzet.zukic.runtime.request.RoleFilter;
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
public class RoleRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param roleFilter Object Used to List Role
   * @param securityContext
   * @return List of Role
   */
  public List<Role> listAllRoles(RoleFilter roleFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Role> q = cb.createQuery(Role.class);
    Root<Role> r = q.from(Role.class);
    List<Predicate> preds = new ArrayList<>();
    addRolePredicate(roleFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Role> query = em.createQuery(q);

    if (roleFilter.getPageSize() != null
        && roleFilter.getCurrentPage() != null
        && roleFilter.getPageSize() > 0
        && roleFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(roleFilter.getPageSize() * roleFilter.getCurrentPage())
          .setMaxResults(roleFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Role> void addRolePredicate(
      RoleFilter roleFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(roleFilter, cb, q, r, preds, securityContext);

    if (roleFilter.getRoleEmployeeToActivitieses() != null
        && !roleFilter.getRoleEmployeeToActivitieses().isEmpty()) {
      Set<String> ids =
          roleFilter.getRoleEmployeeToActivitieses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, EmployeeToActivity> join = r.join(Role_.roleEmployeeToActivities);
      preds.add(join.get(EmployeeToActivity_.id).in(ids));
    }
  }

  /**
   * @param roleFilter Object Used to List Role
   * @param securityContext
   * @return count of Role
   */
  public Long countAllRoles(RoleFilter roleFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Role> r = q.from(Role.class);
    List<Predicate> preds = new ArrayList<>();
    addRolePredicate(roleFilter, cb, q, r, preds, securityContext);
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
