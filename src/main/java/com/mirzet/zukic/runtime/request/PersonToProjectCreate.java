package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Create PersonToProject */
@IdValid.List({
  @IdValid(
      targetField = "person",
      field = "personId",
      fieldType = Person.class,
      groups = {Update.class, Create.class}),
  @IdValid(
      targetField = "project",
      field = "projectId",
      fieldType = Project.class,
      groups = {Update.class, Create.class})
})
public class PersonToProjectCreate {

  @JsonIgnore private Person person;

  private String personId;

  @JsonIgnore private Project project;

  private String projectId;

  /**
   * @return person
   */
  @JsonIgnore
  public Person getPerson() {
    return this.person;
  }

  /**
   * @param person person to set
   * @return PersonToProjectCreate
   */
  public <T extends PersonToProjectCreate> T setPerson(Person person) {
    this.person = person;
    return (T) this;
  }

  /**
   * @return personId
   */
  public String getPersonId() {
    return this.personId;
  }

  /**
   * @param personId personId to set
   * @return PersonToProjectCreate
   */
  public <T extends PersonToProjectCreate> T setPersonId(String personId) {
    this.personId = personId;
    return (T) this;
  }

  /**
   * @return project
   */
  @JsonIgnore
  public Project getProject() {
    return this.project;
  }

  /**
   * @param project project to set
   * @return PersonToProjectCreate
   */
  public <T extends PersonToProjectCreate> T setProject(Project project) {
    this.project = project;
    return (T) this;
  }

  /**
   * @return projectId
   */
  public String getProjectId() {
    return this.projectId;
  }

  /**
   * @param projectId projectId to set
   * @return PersonToProjectCreate
   */
  public <T extends PersonToProjectCreate> T setProjectId(String projectId) {
    this.projectId = projectId;
    return (T) this;
  }
}
