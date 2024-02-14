package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.model.PersonToProject_;
import com.mirzet.zukic.runtime.model.Person_;
import com.mirzet.zukic.runtime.request.PersonFilter;
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
public class PersonRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param personFilter Object Used to List Person
   * @param securityContext
   * @return List of Person
   */
  public List<Person> listAllPersons(
      PersonFilter personFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Person> q = cb.createQuery(Person.class);
    Root<Person> r = q.from(Person.class);
    List<Predicate> preds = new ArrayList<>();
    addPersonPredicate(personFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Person> query = em.createQuery(q);

    if (personFilter.getPageSize() != null
        && personFilter.getCurrentPage() != null
        && personFilter.getPageSize() > 0
        && personFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(personFilter.getPageSize() * personFilter.getCurrentPage())
          .setMaxResults(personFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Person> void addPersonPredicate(
      PersonFilter personFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(personFilter, cb, q, r, preds, securityContext);

    if (personFilter.getEmail() != null && !personFilter.getEmail().isEmpty()) {
      preds.add(r.get(Person_.email).in(personFilter.getEmail()));
    }

    if (personFilter.getPhoneNumber() != null && !personFilter.getPhoneNumber().isEmpty()) {
      preds.add(r.get(Person_.phoneNumber).in(personFilter.getPhoneNumber()));
    }

    if (personFilter.getLastName() != null && !personFilter.getLastName().isEmpty()) {
      preds.add(r.get(Person_.lastName).in(personFilter.getLastName()));
    }

    if (personFilter.getPersonPersonToProjectses() != null
        && !personFilter.getPersonPersonToProjectses().isEmpty()) {
      Set<String> ids =
          personFilter.getPersonPersonToProjectses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, PersonToProject> join = r.join(Person_.personPersonToProjects);
      preds.add(join.get(PersonToProject_.id).in(ids));
    }

    if (personFilter.getFirstName() != null && !personFilter.getFirstName().isEmpty()) {
      preds.add(r.get(Person_.firstName).in(personFilter.getFirstName()));
    }

    if (personFilter.getUsername() != null && !personFilter.getUsername().isEmpty()) {
      preds.add(r.get(Person_.username).in(personFilter.getUsername()));
    }
  }

  /**
   * @param personFilter Object Used to List Person
   * @param securityContext
   * @return count of Person
   */
  public Long countAllPersons(PersonFilter personFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Person> r = q.from(Person.class);
    List<Predicate> preds = new ArrayList<>();
    addPersonPredicate(personFilter, cb, q, r, preds, securityContext);
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
