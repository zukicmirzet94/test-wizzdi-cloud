package com.mirzet.zukic.runtime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PersonToProject {

  @Id private String id;

  @ManyToOne(targetEntity = Person.class)
  private Person person;

  @ManyToOne(targetEntity = Project.class)
  private Project project;

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return PersonToProject
   */
  public <T extends PersonToProject> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return person
   */
  @ManyToOne(targetEntity = Person.class)
  public Person getPerson() {
    return this.person;
  }

  /**
   * @param person person to set
   * @return PersonToProject
   */
  public <T extends PersonToProject> T setPerson(Person person) {
    this.person = person;
    return (T) this;
  }

  /**
   * @return project
   */
  @ManyToOne(targetEntity = Project.class)
  public Project getProject() {
    return this.project;
  }

  /**
   * @param project project to set
   * @return PersonToProject
   */
  public <T extends PersonToProject> T setProject(Project project) {
    this.project = project;
    return (T) this;
  }
}
