package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List PersonToProject */
@IdValid.List({
  @IdValid(targetField = "persons", field = "personIds", fieldType = Person.class),
  @IdValid(targetField = "projects", field = "projectIds", fieldType = Project.class)
})
public class PersonToProjectFilter {

  @Min(value = 0)
  private Integer currentPage;

  private Set<String> id;

  @Min(value = 1)
  private Integer pageSize;

  private Set<String> personIds;

  @JsonIgnore private List<Person> persons;

  private Set<String> projectIds;

  @JsonIgnore private List<Project> projects;

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage currentPage to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return id
   */
  public Set<String> getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return pageSize
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param pageSize pageSize to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }

  /**
   * @return personIds
   */
  public Set<String> getPersonIds() {
    return this.personIds;
  }

  /**
   * @param personIds personIds to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setPersonIds(Set<String> personIds) {
    this.personIds = personIds;
    return (T) this;
  }

  /**
   * @return persons
   */
  @JsonIgnore
  public List<Person> getPersons() {
    return this.persons;
  }

  /**
   * @param persons persons to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setPersons(List<Person> persons) {
    this.persons = persons;
    return (T) this;
  }

  /**
   * @return projectIds
   */
  public Set<String> getProjectIds() {
    return this.projectIds;
  }

  /**
   * @param projectIds projectIds to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setProjectIds(Set<String> projectIds) {
    this.projectIds = projectIds;
    return (T) this;
  }

  /**
   * @return projects
   */
  @JsonIgnore
  public List<Project> getProjects() {
    return this.projects;
  }

  /**
   * @param projects projects to set
   * @return PersonToProjectFilter
   */
  public <T extends PersonToProjectFilter> T setProjects(List<Project> projects) {
    this.projects = projects;
    return (T) this;
  }
}
