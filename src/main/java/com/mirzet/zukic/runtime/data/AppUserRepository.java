package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.AppUser;
import com.mirzet.zukic.runtime.model.AppUser_;
import com.mirzet.zukic.runtime.request.AppUserFilter;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppUserRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param appUserFilter Object Used to List AppUser
   * @param securityContext
   * @return List of AppUser
   */
  public List<AppUser> listAllAppUsers(
      AppUserFilter appUserFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<AppUser> q = cb.createQuery(AppUser.class);
    Root<AppUser> r = q.from(AppUser.class);
    List<Predicate> preds = new ArrayList<>();
    addAppUserPredicate(appUserFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<AppUser> query = em.createQuery(q);

    if (appUserFilter.getPageSize() != null
        && appUserFilter.getCurrentPage() != null
        && appUserFilter.getPageSize() > 0
        && appUserFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(appUserFilter.getPageSize() * appUserFilter.getCurrentPage())
          .setMaxResults(appUserFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends AppUser> void addAppUserPredicate(
      AppUserFilter appUserFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (appUserFilter.getRoles() != null && !appUserFilter.getRoles().isEmpty()) {
      preds.add(r.get(AppUser_.roles).in(appUserFilter.getRoles()));
    }

    if (appUserFilter.getId() != null && !appUserFilter.getId().isEmpty()) {
      preds.add(r.get(AppUser_.id).in(appUserFilter.getId()));
    }

    if (appUserFilter.getUsername() != null && !appUserFilter.getUsername().isEmpty()) {
      preds.add(r.get(AppUser_.username).in(appUserFilter.getUsername()));
    }
  }

  /**
   * @param appUserFilter Object Used to List AppUser
   * @param securityContext
   * @return count of AppUser
   */
  public Long countAllAppUsers(AppUserFilter appUserFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<AppUser> r = q.from(AppUser.class);
    List<Predicate> preds = new ArrayList<>();
    addAppUserPredicate(appUserFilter, cb, q, r, preds, securityContext);
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
