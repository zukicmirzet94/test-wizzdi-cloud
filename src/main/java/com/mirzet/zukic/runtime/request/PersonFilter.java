package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.validation.IdValid;
import java.util.List;
import java.util.Set;

/** Object Used to List Person */
@IdValid.List({
  @IdValid(
      targetField = "personPersonToProjectses",
      field = "personPersonToProjectsIds",
      fieldType = PersonToProject.class)
})
public class PersonFilter extends BasicFilter {

  private Set<String> email;

  private Set<String> firstName;

  private Set<String> lastName;

  private Set<String> personPersonToProjectsIds;

  @JsonIgnore private List<PersonToProject> personPersonToProjectses;

  private Set<String> phoneNumber;

  private Set<String> username;

  /**
   * @return email
   */
  public Set<String> getEmail() {
    return this.email;
  }

  /**
   * @param email email to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setEmail(Set<String> email) {
    this.email = email;
    return (T) this;
  }

  /**
   * @return firstName
   */
  public Set<String> getFirstName() {
    return this.firstName;
  }

  /**
   * @param firstName firstName to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setFirstName(Set<String> firstName) {
    this.firstName = firstName;
    return (T) this;
  }

  /**
   * @return lastName
   */
  public Set<String> getLastName() {
    return this.lastName;
  }

  /**
   * @param lastName lastName to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setLastName(Set<String> lastName) {
    this.lastName = lastName;
    return (T) this;
  }

  /**
   * @return personPersonToProjectsIds
   */
  public Set<String> getPersonPersonToProjectsIds() {
    return this.personPersonToProjectsIds;
  }

  /**
   * @param personPersonToProjectsIds personPersonToProjectsIds to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setPersonPersonToProjectsIds(
      Set<String> personPersonToProjectsIds) {
    this.personPersonToProjectsIds = personPersonToProjectsIds;
    return (T) this;
  }

  /**
   * @return personPersonToProjectses
   */
  @JsonIgnore
  public List<PersonToProject> getPersonPersonToProjectses() {
    return this.personPersonToProjectses;
  }

  /**
   * @param personPersonToProjectses personPersonToProjectses to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setPersonPersonToProjectses(
      List<PersonToProject> personPersonToProjectses) {
    this.personPersonToProjectses = personPersonToProjectses;
    return (T) this;
  }

  /**
   * @return phoneNumber
   */
  public Set<String> getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * @param phoneNumber phoneNumber to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setPhoneNumber(Set<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
    return (T) this;
  }

  /**
   * @return username
   */
  public Set<String> getUsername() {
    return this.username;
  }

  /**
   * @param username username to set
   * @return PersonFilter
   */
  public <T extends PersonFilter> T setUsername(Set<String> username) {
    this.username = username;
    return (T) this;
  }
}
