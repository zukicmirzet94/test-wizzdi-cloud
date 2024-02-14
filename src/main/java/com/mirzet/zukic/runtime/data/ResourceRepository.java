package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.Resource_;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.Task_;
import com.mirzet.zukic.runtime.request.ResourceFilter;
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
public class ResourceRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param resourceFilter Object Used to List Resource
   * @param securityContext
   * @return List of Resource
   */
  public List<Resource> listAllResources(
      ResourceFilter resourceFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Resource> q = cb.createQuery(Resource.class);
    Root<Resource> r = q.from(Resource.class);
    List<Predicate> preds = new ArrayList<>();
    addResourcePredicate(resourceFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Resource> query = em.createQuery(q);

    if (resourceFilter.getPageSize() != null
        && resourceFilter.getCurrentPage() != null
        && resourceFilter.getPageSize() > 0
        && resourceFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(resourceFilter.getPageSize() * resourceFilter.getCurrentPage())
          .setMaxResults(resourceFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Resource> void addResourcePredicate(
      ResourceFilter resourceFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(resourceFilter, cb, q, r, preds, securityContext);

    if (resourceFilter.getResourceTaskses() != null
        && !resourceFilter.getResourceTaskses().isEmpty()) {
      Set<String> ids =
          resourceFilter.getResourceTaskses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Task> join = r.join(Resource_.resourceTasks);
      preds.add(join.get(Task_.id).in(ids));
    }
  }

  /**
   * @param resourceFilter Object Used to List Resource
   * @param securityContext
   * @return count of Resource
   */
  public Long countAllResources(
      ResourceFilter resourceFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Resource> r = q.from(Resource.class);
    List<Predicate> preds = new ArrayList<>();
    addResourcePredicate(resourceFilter, cb, q, r, preds, securityContext);
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
