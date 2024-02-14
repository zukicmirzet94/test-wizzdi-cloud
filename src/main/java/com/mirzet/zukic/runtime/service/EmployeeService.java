package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.EmployeeRepository;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.request.EmployeeCreate;
import com.mirzet.zukic.runtime.request.EmployeeFilter;
import com.mirzet.zukic.runtime.request.EmployeeUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {

  @Autowired private EmployeeRepository repository;

  @Autowired private PersonService personService;

  /**
   * @param employeeCreate Object Used to Create Employee
   * @param securityContext
   * @return created Employee
   */
  public Employee createEmployee(
      EmployeeCreate employeeCreate, UserSecurityContext securityContext) {
    Employee employee = createEmployeeNoMerge(employeeCreate, securityContext);
    this.repository.merge(employee);
    return employee;
  }

  /**
   * @param employeeCreate Object Used to Create Employee
   * @param securityContext
   * @return created Employee unmerged
   */
  public Employee createEmployeeNoMerge(
      EmployeeCreate employeeCreate, UserSecurityContext securityContext) {
    Employee employee = new Employee();
    employee.setId(UUID.randomUUID().toString());
    updateEmployeeNoMerge(employee, employeeCreate);

    return employee;
  }

  /**
   * @param employeeCreate Object Used to Create Employee
   * @param employee
   * @return if employee was updated
   */
  public boolean updateEmployeeNoMerge(Employee employee, EmployeeCreate employeeCreate) {
    boolean update = personService.updatePersonNoMerge(employee, employeeCreate);

    if (employeeCreate.getOrganization() != null
        && (employee.getOrganization() == null
            || !employeeCreate
                .getOrganization()
                .getId()
                .equals(employee.getOrganization().getId()))) {
      employee.setOrganization(employeeCreate.getOrganization());
      update = true;
    }

    if (employeeCreate.getQualification() != null
        && (employee.getQualification() == null
            || !employeeCreate
                .getQualification()
                .getId()
                .equals(employee.getQualification().getId()))) {
      employee.setQualification(employeeCreate.getQualification());
      update = true;
    }

    return update;
  }

  /**
   * @param employeeUpdate
   * @param securityContext
   * @return employee
   */
  public Employee updateEmployee(
      EmployeeUpdate employeeUpdate, UserSecurityContext securityContext) {
    Employee employee = employeeUpdate.getEmployee();
    if (updateEmployeeNoMerge(employee, employeeUpdate)) {
      this.repository.merge(employee);
    }
    return employee;
  }

  /**
   * @param employeeFilter Object Used to List Employee
   * @param securityContext
   * @return PaginationResponse<Employee> containing paging information for Employee
   */
  public PaginationResponse<Employee> getAllEmployees(
      EmployeeFilter employeeFilter, UserSecurityContext securityContext) {
    List<Employee> list = listAllEmployees(employeeFilter, securityContext);
    long count = this.repository.countAllEmployees(employeeFilter, securityContext);
    return new PaginationResponse<>(list, employeeFilter.getPageSize(), count);
  }

  /**
   * @param employeeFilter Object Used to List Employee
   * @param securityContext
   * @return List of Employee
   */
  public List<Employee> listAllEmployees(
      EmployeeFilter employeeFilter, UserSecurityContext securityContext) {
    return this.repository.listAllEmployees(employeeFilter, securityContext);
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
