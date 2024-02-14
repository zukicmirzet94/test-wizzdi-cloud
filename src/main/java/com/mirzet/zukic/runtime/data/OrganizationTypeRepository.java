package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.model.OrganizationType_;
import com.mirzet.zukic.runtime.model.Organization_;
import com.mirzet.zukic.runtime.request.OrganizationTypeFilter;
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
public class OrganizationTypeRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param organizationTypeFilter Object Used to List OrganizationType
   * @param securityContext
   * @return List of OrganizationType
   */
  public List<OrganizationType> listAllOrganizationTypes(
      OrganizationTypeFilter organizationTypeFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<OrganizationType> q = cb.createQuery(OrganizationType.class);
    Root<OrganizationType> r = q.from(OrganizationType.class);
    List<Predicate> preds = new ArrayList<>();
    addOrganizationTypePredicate(organizationTypeFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<OrganizationType> query = em.createQuery(q);

    if (organizationTypeFilter.getPageSize() != null
        && organizationTypeFilter.getCurrentPage() != null
        && organizationTypeFilter.getPageSize() > 0
        && organizationTypeFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              organizationTypeFilter.getPageSize() * organizationTypeFilter.getCurrentPage())
          .setMaxResults(organizationTypeFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends OrganizationType> void addOrganizationTypePredicate(
      OrganizationTypeFilter organizationTypeFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(organizationTypeFilter, cb, q, r, preds, securityContext);

    if (organizationTypeFilter.getOrganizationTypeOrganizationses() != null
        && !organizationTypeFilter.getOrganizationTypeOrganizationses().isEmpty()) {
      Set<String> ids =
          organizationTypeFilter.getOrganizationTypeOrganizationses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Organization> join = r.join(OrganizationType_.organizationTypeOrganizations);
      preds.add(join.get(Organization_.id).in(ids));
    }
  }

  /**
   * @param organizationTypeFilter Object Used to List OrganizationType
   * @param securityContext
   * @return count of OrganizationType
   */
  public Long countAllOrganizationTypes(
      OrganizationTypeFilter organizationTypeFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<OrganizationType> r = q.from(OrganizationType.class);
    List<Predicate> preds = new ArrayList<>();
    addOrganizationTypePredicate(organizationTypeFilter, cb, q, r, preds, securityContext);
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
