package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update PersonToProject */
@IdValid.List({
  @IdValid(
      targetField = "personToProject",
      field = "id",
      fieldType = PersonToProject.class,
      groups = {Update.class})
})
public class PersonToProjectUpdate extends PersonToProjectCreate {

  private String id;

  @JsonIgnore private PersonToProject personToProject;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return PersonToProjectUpdate
   */
  public <T extends PersonToProjectUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return personToProject
   */
  @JsonIgnore
  public PersonToProject getPersonToProject() {
    return this.personToProject;
  }

  /**
   * @param personToProject personToProject to set
   * @return PersonToProjectUpdate
   */
  public <T extends PersonToProjectUpdate> T setPersonToProject(PersonToProject personToProject) {
    this.personToProject = personToProject;
    return (T) this;
  }
}
