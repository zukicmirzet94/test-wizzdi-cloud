package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.PersonToProjectRepository;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.request.PersonToProjectCreate;
import com.mirzet.zukic.runtime.request.PersonToProjectFilter;
import com.mirzet.zukic.runtime.request.PersonToProjectUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonToProjectService {

  @Autowired private PersonToProjectRepository repository;

  /**
   * @param personToProjectCreate Object Used to Create PersonToProject
   * @param securityContext
   * @return created PersonToProject
   */
  public PersonToProject createPersonToProject(
      PersonToProjectCreate personToProjectCreate, UserSecurityContext securityContext) {
    PersonToProject personToProject =
        createPersonToProjectNoMerge(personToProjectCreate, securityContext);
    this.repository.merge(personToProject);
    return personToProject;
  }

  /**
   * @param personToProjectCreate Object Used to Create PersonToProject
   * @param securityContext
   * @return created PersonToProject unmerged
   */
  public PersonToProject createPersonToProjectNoMerge(
      PersonToProjectCreate personToProjectCreate, UserSecurityContext securityContext) {
    PersonToProject personToProject = new PersonToProject();
    personToProject.setId(UUID.randomUUID().toString());
    updatePersonToProjectNoMerge(personToProject, personToProjectCreate);

    return personToProject;
  }

  /**
   * @param personToProjectCreate Object Used to Create PersonToProject
   * @param personToProject
   * @return if personToProject was updated
   */
  public boolean updatePersonToProjectNoMerge(
      PersonToProject personToProject, PersonToProjectCreate personToProjectCreate) {
    boolean update = false;

    if (personToProjectCreate.getProject() != null
        && (personToProject.getProject() == null
            || !personToProjectCreate
                .getProject()
                .getId()
                .equals(personToProject.getProject().getId()))) {
      personToProject.setProject(personToProjectCreate.getProject());
      update = true;
    }

    if (personToProjectCreate.getPerson() != null
        && (personToProject.getPerson() == null
            || !personToProjectCreate
                .getPerson()
                .getId()
                .equals(personToProject.getPerson().getId()))) {
      personToProject.setPerson(personToProjectCreate.getPerson());
      update = true;
    }

    return update;
  }

  /**
   * @param personToProjectUpdate
   * @param securityContext
   * @return personToProject
   */
  public PersonToProject updatePersonToProject(
      PersonToProjectUpdate personToProjectUpdate, UserSecurityContext securityContext) {
    PersonToProject personToProject = personToProjectUpdate.getPersonToProject();
    if (updatePersonToProjectNoMerge(personToProject, personToProjectUpdate)) {
      this.repository.merge(personToProject);
    }
    return personToProject;
  }

  /**
   * @param personToProjectFilter Object Used to List PersonToProject
   * @param securityContext
   * @return PaginationResponse<PersonToProject> containing paging information for PersonToProject
   */
  public PaginationResponse<PersonToProject> getAllPersonToProjects(
      PersonToProjectFilter personToProjectFilter, UserSecurityContext securityContext) {
    List<PersonToProject> list = listAllPersonToProjects(personToProjectFilter, securityContext);
    long count = this.repository.countAllPersonToProjects(personToProjectFilter, securityContext);
    return new PaginationResponse<>(list, personToProjectFilter.getPageSize(), count);
  }

  /**
   * @param personToProjectFilter Object Used to List PersonToProject
   * @param securityContext
   * @return List of PersonToProject
   */
  public List<PersonToProject> listAllPersonToProjects(
      PersonToProjectFilter personToProjectFilter, UserSecurityContext securityContext) {
    return this.repository.listAllPersonToProjects(personToProjectFilter, securityContext);
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public void merge(java.lang.Object base) {
    this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}
