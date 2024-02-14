package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.model.PersonToProject_;
import com.mirzet.zukic.runtime.model.Person_;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Project_;
import com.mirzet.zukic.runtime.request.PersonToProjectFilter;
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
public class PersonToProjectRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param personToProjectFilter Object Used to List PersonToProject
   * @param securityContext
   * @return List of PersonToProject
   */
  public List<PersonToProject> listAllPersonToProjects(
      PersonToProjectFilter personToProjectFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<PersonToProject> q = cb.createQuery(PersonToProject.class);
    Root<PersonToProject> r = q.from(PersonToProject.class);
    List<Predicate> preds = new ArrayList<>();
    addPersonToProjectPredicate(personToProjectFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<PersonToProject> query = em.createQuery(q);

    if (personToProjectFilter.getPageSize() != null
        && personToProjectFilter.getCurrentPage() != null
        && personToProjectFilter.getPageSize() > 0
        && personToProjectFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              personToProjectFilter.getPageSize() * personToProjectFilter.getCurrentPage())
          .setMaxResults(personToProjectFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends PersonToProject> void addPersonToProjectPredicate(
      PersonToProjectFilter personToProjectFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (personToProjectFilter.getId() != null && !personToProjectFilter.getId().isEmpty()) {
      preds.add(r.get(PersonToProject_.id).in(personToProjectFilter.getId()));
    }

    if (personToProjectFilter.getProjects() != null
        && !personToProjectFilter.getProjects().isEmpty()) {
      Set<String> ids =
          personToProjectFilter.getProjects().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Project> join = r.join(PersonToProject_.project);
      preds.add(join.get(Project_.id).in(ids));
    }

    if (personToProjectFilter.getPersons() != null
        && !personToProjectFilter.getPersons().isEmpty()) {
      Set<String> ids =
          personToProjectFilter.getPersons().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Person> join = r.join(PersonToProject_.person);
      preds.add(join.get(Person_.id).in(ids));
    }
  }

  /**
   * @param personToProjectFilter Object Used to List PersonToProject
   * @param securityContext
   * @return count of PersonToProject
   */
  public Long countAllPersonToProjects(
      PersonToProjectFilter personToProjectFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<PersonToProject> r = q.from(PersonToProject.class);
    List<Predicate> preds = new ArrayList<>();
    addPersonToProjectPredicate(personToProjectFilter, cb, q, r, preds, securityContext);
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
