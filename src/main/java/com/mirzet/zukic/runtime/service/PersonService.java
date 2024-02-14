package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.PersonRepository;
import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.request.PersonCreate;
import com.mirzet.zukic.runtime.request.PersonFilter;
import com.mirzet.zukic.runtime.request.PersonUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

  @Autowired private PersonRepository repository;

  @Autowired private BasicService basicService;

  @Autowired private PasswordEncoder passwordEncoder;

  /**
   * @param personCreate Object Used to Create Person
   * @param securityContext
   * @return created Person
   */
  public Person createPerson(PersonCreate personCreate, UserSecurityContext securityContext) {
    Person person = createPersonNoMerge(personCreate, securityContext);
    this.repository.merge(person);
    return person;
  }

  /**
   * @param personCreate Object Used to Create Person
   * @param securityContext
   * @return created Person unmerged
   */
  public Person createPersonNoMerge(
      PersonCreate personCreate, UserSecurityContext securityContext) {
    Person person = new Person();
    person.setId(UUID.randomUUID().toString());
    updatePersonNoMerge(person, personCreate);

    return person;
  }

  /**
   * @param personCreate Object Used to Create Person
   * @param person
   * @return if person was updated
   */
  public boolean updatePersonNoMerge(Person person, PersonCreate personCreate) {
    boolean update = basicService.updateBasicNoMerge(person, personCreate);

    if (personCreate.getEmail() != null && (!personCreate.getEmail().equals(person.getEmail()))) {
      person.setEmail(personCreate.getEmail());
      update = true;
    }

    if (personCreate.getPhoneNumber() != null
        && (!personCreate.getPhoneNumber().equals(person.getPhoneNumber()))) {
      person.setPhoneNumber(personCreate.getPhoneNumber());
      update = true;
    }

    if (personCreate.getPassword() != null
        && (!passwordEncoder.matches(personCreate.getPassword(), person.getPassword()))) {
      person.setPassword(passwordEncoder.encode(personCreate.getPassword()));
      update = true;
    }

    if (personCreate.getLastName() != null
        && (!personCreate.getLastName().equals(person.getLastName()))) {
      person.setLastName(personCreate.getLastName());
      update = true;
    }

    if (personCreate.getFirstName() != null
        && (!personCreate.getFirstName().equals(person.getFirstName()))) {
      person.setFirstName(personCreate.getFirstName());
      update = true;
    }

    if (personCreate.getUsername() != null
        && (!personCreate.getUsername().equals(person.getUsername()))) {
      person.setUsername(personCreate.getUsername());
      update = true;
    }

    return update;
  }

  /**
   * @param personUpdate
   * @param securityContext
   * @return person
   */
  public Person updatePerson(PersonUpdate personUpdate, UserSecurityContext securityContext) {
    Person person = personUpdate.getPerson();
    if (updatePersonNoMerge(person, personUpdate)) {
      this.repository.merge(person);
    }
    return person;
  }

  /**
   * @param personFilter Object Used to List Person
   * @param securityContext
   * @return PaginationResponse<Person> containing paging information for Person
   */
  public PaginationResponse<Person> getAllPersons(
      PersonFilter personFilter, UserSecurityContext securityContext) {
    List<Person> list = listAllPersons(personFilter, securityContext);
    long count = this.repository.countAllPersons(personFilter, securityContext);
    return new PaginationResponse<>(list, personFilter.getPageSize(), count);
  }

  /**
   * @param personFilter Object Used to List Person
   * @param securityContext
   * @return List of Person
   */
  public List<Person> listAllPersons(
      PersonFilter personFilter, UserSecurityContext securityContext) {
    return this.repository.listAllPersons(personFilter, securityContext);
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
