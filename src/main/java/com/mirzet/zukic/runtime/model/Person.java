package com.mirzet.zukic.runtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Person extends Basic {

  private String firstName;

  @OneToMany(targetEntity = PersonToProject.class, mappedBy = "person")
  @JsonIgnore
  private List<PersonToProject> personPersonToProjects;

  private String lastName;

  private String email;

  private String phoneNumber;

  private String username;

  private String password;

  /**
   * @return firstName
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * @param firstName firstName to set
   * @return Person
   */
  public <T extends Person> T setFirstName(String firstName) {
    this.firstName = firstName;
    return (T) this;
  }

  /**
   * @return personPersonToProjects
   */
  @OneToMany(targetEntity = PersonToProject.class, mappedBy = "person")
  @JsonIgnore
  public List<PersonToProject> getPersonPersonToProjects() {
    return this.personPersonToProjects;
  }

  /**
   * @param personPersonToProjects personPersonToProjects to set
   * @return Person
   */
  public <T extends Person> T setPersonPersonToProjects(
      List<PersonToProject> personPersonToProjects) {
    this.personPersonToProjects = personPersonToProjects;
    return (T) this;
  }

  /**
   * @return lastName
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * @param lastName lastName to set
   * @return Person
   */
  public <T extends Person> T setLastName(String lastName) {
    this.lastName = lastName;
    return (T) this;
  }

  /**
   * @return email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email email to set
   * @return Person
   */
  public <T extends Person> T setEmail(String email) {
    this.email = email;
    return (T) this;
  }

  /**
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * @param phoneNumber phoneNumber to set
   * @return Person
   */
  public <T extends Person> T setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return (T) this;
  }

  /**
   * @return username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @param username username to set
   * @return Person
   */
  public <T extends Person> T setUsername(String username) {
    this.username = username;
    return (T) this;
  }

  /**
   * @return password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password password to set
   * @return Person
   */
  public <T extends Person> T setPassword(String password) {
    this.password = password;
    return (T) this;
  }
}
