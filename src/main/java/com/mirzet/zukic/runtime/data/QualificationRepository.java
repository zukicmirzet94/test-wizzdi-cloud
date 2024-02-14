package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Employee_;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.model.Qualification_;
import com.mirzet.zukic.runtime.request.QualificationFilter;
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
public class QualificationRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param qualificationFilter Object Used to List Qualification
   * @param securityContext
   * @return List of Qualification
   */
  public List<Qualification> listAllQualifications(
      QualificationFilter qualificationFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Qualification> q = cb.createQuery(Qualification.class);
    Root<Qualification> r = q.from(Qualification.class);
    List<Predicate> preds = new ArrayList<>();
    addQualificationPredicate(qualificationFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Qualification> query = em.createQuery(q);

    if (qualificationFilter.getPageSize() != null
        && qualificationFilter.getCurrentPage() != null
        && qualificationFilter.getPageSize() > 0
        && qualificationFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(qualificationFilter.getPageSize() * qualificationFilter.getCurrentPage())
          .setMaxResults(qualificationFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Qualification> void addQualificationPredicate(
      QualificationFilter qualificationFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(qualificationFilter, cb, q, r, preds, securityContext);

    if (qualificationFilter.getQualificationEmployeeses() != null
        && !qualificationFilter.getQualificationEmployeeses().isEmpty()) {
      Set<String> ids =
          qualificationFilter.getQualificationEmployeeses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Employee> join = r.join(Qualification_.qualificationEmployees);
      preds.add(join.get(Employee_.id).in(ids));
    }
  }

  /**
   * @param qualificationFilter Object Used to List Qualification
   * @param securityContext
   * @return count of Qualification
   */
  public Long countAllQualifications(
      QualificationFilter qualificationFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Qualification> r = q.from(Qualification.class);
    List<Predicate> preds = new ArrayList<>();
    addQualificationPredicate(qualificationFilter, cb, q, r, preds, securityContext);
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
