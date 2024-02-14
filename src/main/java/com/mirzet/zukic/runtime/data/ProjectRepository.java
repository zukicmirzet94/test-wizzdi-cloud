package com.mirzet.zukic.runtime.data;

import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.model.Client_;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.model.PersonToProject_;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Project_;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.Task_;
import com.mirzet.zukic.runtime.request.ProjectFilter;
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
public class ProjectRepository {
  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private BasicRepository basicRepository;

  /**
   * @param projectFilter Object Used to List Project
   * @param securityContext
   * @return List of Project
   */
  public List<Project> listAllProjects(
      ProjectFilter projectFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Project> q = cb.createQuery(Project.class);
    Root<Project> r = q.from(Project.class);
    List<Predicate> preds = new ArrayList<>();
    addProjectPredicate(projectFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<Project> query = em.createQuery(q);

    if (projectFilter.getPageSize() != null
        && projectFilter.getCurrentPage() != null
        && projectFilter.getPageSize() > 0
        && projectFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(projectFilter.getPageSize() * projectFilter.getCurrentPage())
          .setMaxResults(projectFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends Project> void addProjectPredicate(
      ProjectFilter projectFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    basicRepository.addBasicPredicate(projectFilter, cb, q, r, preds, securityContext);

    if (projectFilter.getProjectTaskses() != null && !projectFilter.getProjectTaskses().isEmpty()) {
      Set<String> ids =
          projectFilter.getProjectTaskses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Task> join = r.join(Project_.projectTasks);
      preds.add(join.get(Task_.id).in(ids));
    }

    if (projectFilter.getActualEndDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Project_.actualEndDate), projectFilter.getActualEndDateStart()));
    }
    if (projectFilter.getActualEndDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(r.get(Project_.actualEndDate), projectFilter.getActualEndDateEnd()));
    }

    if (projectFilter.getPlannedStartDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Project_.plannedStartDate), projectFilter.getPlannedStartDateStart()));
    }
    if (projectFilter.getPlannedStartDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Project_.plannedStartDate), projectFilter.getPlannedStartDateEnd()));
    }

    if (projectFilter.getClients() != null && !projectFilter.getClients().isEmpty()) {
      Set<String> ids =
          projectFilter.getClients().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Client> join = r.join(Project_.client);
      preds.add(join.get(Client_.id).in(ids));
    }

    if (projectFilter.getActualStartDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Project_.actualStartDate), projectFilter.getActualStartDateStart()));
    }
    if (projectFilter.getActualStartDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Project_.actualStartDate), projectFilter.getActualStartDateEnd()));
    }

    if (projectFilter.getPlannedEndDateStart() != null) {
      preds.add(
          cb.greaterThanOrEqualTo(
              r.get(Project_.plannedEndDate), projectFilter.getPlannedEndDateStart()));
    }
    if (projectFilter.getPlannedEndDateEnd() != null) {
      preds.add(
          cb.lessThanOrEqualTo(
              r.get(Project_.plannedEndDate), projectFilter.getPlannedEndDateEnd()));
    }

    if (projectFilter.getProjectPersonToProjectses() != null
        && !projectFilter.getProjectPersonToProjectses().isEmpty()) {
      Set<String> ids =
          projectFilter.getProjectPersonToProjectses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, PersonToProject> join = r.join(Project_.projectPersonToProjects);
      preds.add(join.get(PersonToProject_.id).in(ids));
    }
  }

  /**
   * @param projectFilter Object Used to List Project
   * @param securityContext
   * @return count of Project
   */
  public Long countAllProjects(ProjectFilter projectFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Project> r = q.from(Project.class);
    List<Predicate> preds = new ArrayList<>();
    addProjectPredicate(projectFilter, cb, q, r, preds, securityContext);
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
