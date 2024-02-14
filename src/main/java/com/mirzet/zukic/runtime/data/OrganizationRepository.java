package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Employee_;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.model.OrganizationType_;
import com.mirzet.zukic.runtime.model.Organization_;
import com.mirzet.zukic.runtime.request.OrganizationFilter;
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
public class OrganizationRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param organizationFilter Object Used to List Organization
   * @param securityContext
   * @return List of Organization
   */
  public List<Organization> listAllOrganizations(
      OrganizationFilter organizationFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Organization> q = cb.createQuery(Organization.class);
    Root<Organization> r = q.from(Organization.class);
    List<Predicate> preds = new ArrayList<>();
    addOrganizationPredicate(organizationFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Organization> query = em.createQuery(q);

    if (organizationFilter.getPageSize() != null
        && organizationFilter.getCurrentPage() != null
        && organizationFilter.getPageSize() > 0
        && organizationFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(organizationFilter.getPageSize() * organizationFilter.getCurrentPage())
          .setMaxResults(organizationFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Organization> void addOrganizationPredicate(
      OrganizationFilter organizationFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(organizationFilter, cb, q, r, preds, securityContext);

    if (organizationFilter.getCountry() != null && !organizationFilter.getCountry().isEmpty()) {
      preds.add(r.get(Organization_.country).in(organizationFilter.getCountry()));
    }

    if (organizationFilter.getOrganizationEmployeeses() != null
        && !organizationFilter.getOrganizationEmployeeses().isEmpty()) {
      Set<String> ids =
          organizationFilter.getOrganizationEmployeeses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Employee> join = r.join(Organization_.organizationEmployees);
      preds.add(join.get(Employee_.id).in(ids));
    }

    if (organizationFilter.getOrganizationTypes() != null
        && !organizationFilter.getOrganizationTypes().isEmpty()) {
      Set<String> ids =
          organizationFilter.getOrganizationTypes().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, OrganizationType> join = r.join(Organization_.organizationType);
      preds.add(join.get(OrganizationType_.id).in(ids));
    }

    if (organizationFilter.getType() != null && !organizationFilter.getType().isEmpty()) {
      preds.add(r.get(Organization_.type).in(organizationFilter.getType()));
    }
  }

  /**
   * @param organizationFilter Object Used to List Organization
   * @param securityContext
   * @return count of Organization
   */
  public Long countAllOrganizations(
      OrganizationFilter organizationFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Organization> r = q.from(Organization.class);
    List<Predicate> preds = new ArrayList<>();
    addOrganizationPredicate(organizationFilter, cb, q, r, preds, securityContext);
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
