package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.ProjectRepository;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.request.ProjectCreate;
import com.mirzet.zukic.runtime.request.ProjectFilter;
import com.mirzet.zukic.runtime.request.ProjectUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectService {

  @Autowired private ProjectRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param projectCreate Object Used to Create Project
   * @param securityContext
   * @return created Project
   */
  public Project createProject(ProjectCreate projectCreate, UserSecurityContext securityContext) {
    Project project = createProjectNoMerge(projectCreate, securityContext);
    this.repository.merge(project);
    return project;
  }

  /**
   * @param projectCreate Object Used to Create Project
   * @param securityContext
   * @return created Project unmerged
   */
  public Project createProjectNoMerge(
      ProjectCreate projectCreate, UserSecurityContext securityContext) {
    Project project = new Project();
    project.setId(UUID.randomUUID().toString());
    updateProjectNoMerge(project, projectCreate);

    return project;
  }

  /**
   * @param projectCreate Object Used to Create Project
   * @param project
   * @return if project was updated
   */
  public boolean updateProjectNoMerge(Project project, ProjectCreate projectCreate) {
    boolean update = basicService.updateBasicNoMerge(project, projectCreate);

    if (projectCreate.getActualEndDate() != null
        && (!projectCreate.getActualEndDate().equals(project.getActualEndDate()))) {
      project.setActualEndDate(projectCreate.getActualEndDate());
      update = true;
    }

    if (projectCreate.getPlannedStartDate() != null
        && (!projectCreate.getPlannedStartDate().equals(project.getPlannedStartDate()))) {
      project.setPlannedStartDate(projectCreate.getPlannedStartDate());
      update = true;
    }

    if (projectCreate.getClient() != null
        && (project.getClient() == null
            || !projectCreate.getClient().getId().equals(project.getClient().getId()))) {
      project.setClient(projectCreate.getClient());
      update = true;
    }

    if (projectCreate.getActualStartDate() != null
        && (!projectCreate.getActualStartDate().equals(project.getActualStartDate()))) {
      project.setActualStartDate(projectCreate.getActualStartDate());
      update = true;
    }

    if (projectCreate.getPlannedEndDate() != null
        && (!projectCreate.getPlannedEndDate().equals(project.getPlannedEndDate()))) {
      project.setPlannedEndDate(projectCreate.getPlannedEndDate());
      update = true;
    }

    return update;
  }

  /**
   * @param projectUpdate
   * @param securityContext
   * @return project
   */
  public Project updateProject(ProjectUpdate projectUpdate, UserSecurityContext securityContext) {
    Project project = projectUpdate.getProject();
    if (updateProjectNoMerge(project, projectUpdate)) {
      this.repository.merge(project);
    }
    return project;
  }

  /**
   * @param projectFilter Object Used to List Project
   * @param securityContext
   * @return PaginationResponse<Project> containing paging information for Project
   */
  public PaginationResponse<Project> getAllProjects(
      ProjectFilter projectFilter, UserSecurityContext securityContext) {
    List<Project> list = listAllProjects(projectFilter, securityContext);
    long count = this.repository.countAllProjects(projectFilter, securityContext);
    return new PaginationResponse<>(list, projectFilter.getPageSize(), count);
  }

  /**
   * @param projectFilter Object Used to List Project
   * @param securityContext
   * @return List of Project
   */
  public List<Project> listAllProjects(
      ProjectFilter projectFilter, UserSecurityContext securityContext) {
    return this.repository.listAllProjects(projectFilter, securityContext);
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
