package com.mirzet.zukic.runtime.request;

/** Object Used to Create Person */
public class PersonCreate extends BasicCreate {

  private String email;

  private String firstName;

  private String lastName;

  private String password;

  private String phoneNumber;

  private String username;

  /**
   * @return email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email email to set
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setEmail(String email) {
    this.email = email;
    return (T) this;
  }

  /**
   * @return firstName
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * @param firstName firstName to set
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setFirstName(String firstName) {
    this.firstName = firstName;
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
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setLastName(String lastName) {
    this.lastName = lastName;
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
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setPassword(String password) {
    this.password = password;
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
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setPhoneNumber(String phoneNumber) {
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
   * @return PersonCreate
   */
  public <T extends PersonCreate> T setUsername(String username) {
    this.username = username;
    return (T) this;
  }
}
