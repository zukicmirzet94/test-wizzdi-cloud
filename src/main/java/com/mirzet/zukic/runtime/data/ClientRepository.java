package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.model.Client_;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Project_;
import com.mirzet.zukic.runtime.request.ClientFilter;
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
public class ClientRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param clientFilter Object Used to List Client
   * @param securityContext
   * @return List of Client
   */
  public List<Client> listAllClients(
      ClientFilter clientFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Client> q = cb.createQuery(Client.class);
    Root<Client> r = q.from(Client.class);
    List<Predicate> preds = new ArrayList<>();
    addClientPredicate(clientFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Client> query = em.createQuery(q);

    if (clientFilter.getPageSize() != null
        && clientFilter.getCurrentPage() != null
        && clientFilter.getPageSize() > 0
        && clientFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(clientFilter.getPageSize() * clientFilter.getCurrentPage())
          .setMaxResults(clientFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Client> void addClientPredicate(
      ClientFilter clientFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(clientFilter, cb, q, r, preds, securityContext);

    if (clientFilter.getClientProjectses() != null
        && !clientFilter.getClientProjectses().isEmpty()) {
      Set<String> ids =
          clientFilter.getClientProjectses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Project> join = r.join(Client_.clientProjects);
      preds.add(join.get(Project_.id).in(ids));
    }
  }

  /**
   * @param clientFilter Object Used to List Client
   * @param securityContext
   * @return count of Client
   */
  public Long countAllClients(ClientFilter clientFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Client> r = q.from(Client.class);
    List<Predicate> preds = new ArrayList<>();
    addClientPredicate(clientFilter, cb, q, r, preds, securityContext);
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
