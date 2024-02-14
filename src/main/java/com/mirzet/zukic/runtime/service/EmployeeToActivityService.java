package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.EmployeeToActivityRepository;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.request.EmployeeToActivityCreate;
import com.mirzet.zukic.runtime.request.EmployeeToActivityFilter;
import com.mirzet.zukic.runtime.request.EmployeeToActivityUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToActivityService {

  @Autowired private EmployeeToActivityRepository repository;

  /**
   * @param employeeToActivityCreate Object Used to Create EmployeeToActivity
   * @param securityContext
   * @return created EmployeeToActivity
   */
  public EmployeeToActivity createEmployeeToActivity(
      EmployeeToActivityCreate employeeToActivityCreate, UserSecurityContext securityContext) {
    EmployeeToActivity employeeToActivity =
        createEmployeeToActivityNoMerge(employeeToActivityCreate, securityContext);
    this.repository.merge(employeeToActivity);
    return employeeToActivity;
  }

  /**
   * @param employeeToActivityCreate Object Used to Create EmployeeToActivity
   * @param securityContext
   * @return created EmployeeToActivity unmerged
   */
  public EmployeeToActivity createEmployeeToActivityNoMerge(
      EmployeeToActivityCreate employeeToActivityCreate, UserSecurityContext securityContext) {
    EmployeeToActivity employeeToActivity = new EmployeeToActivity();
    employeeToActivity.setId(UUID.randomUUID().toString());
    updateEmployeeToActivityNoMerge(employeeToActivity, employeeToActivityCreate);

    return employeeToActivity;
  }

  /**
   * @param employeeToActivityCreate Object Used to Create EmployeeToActivity
   * @param employeeToActivity
   * @return if employeeToActivity was updated
   */
  public boolean updateEmployeeToActivityNoMerge(
      EmployeeToActivity employeeToActivity, EmployeeToActivityCreate employeeToActivityCreate) {
    boolean update = false;

    if (employeeToActivityCreate.getRole() != null
        && (employeeToActivity.getRole() == null
            || !employeeToActivityCreate
                .getRole()
                .getId()
                .equals(employeeToActivity.getRole().getId()))) {
      employeeToActivity.setRole(employeeToActivityCreate.getRole());
      update = true;
    }

    if (employeeToActivityCreate.getActivity() != null
        && (employeeToActivity.getActivity() == null
            || !employeeToActivityCreate
                .getActivity()
                .getId()
                .equals(employeeToActivity.getActivity().getId()))) {
      employeeToActivity.setActivity(employeeToActivityCreate.getActivity());
      update = true;
    }

    if (employeeToActivityCreate.getEmployee() != null
        && (employeeToActivity.getEmployee() == null
            || !employeeToActivityCreate
                .getEmployee()
                .getId()
                .equals(employeeToActivity.getEmployee().getId()))) {
      employeeToActivity.setEmployee(employeeToActivityCreate.getEmployee());
      update = true;
    }

    return update;
  }

  /**
   * @param employeeToActivityUpdate
   * @param securityContext
   * @return employeeToActivity
   */
  public EmployeeToActivity updateEmployeeToActivity(
      EmployeeToActivityUpdate employeeToActivityUpdate, UserSecurityContext securityContext) {
    EmployeeToActivity employeeToActivity = employeeToActivityUpdate.getEmployeeToActivity();
    if (updateEmployeeToActivityNoMerge(employeeToActivity, employeeToActivityUpdate)) {
      this.repository.merge(employeeToActivity);
    }
    return employeeToActivity;
  }

  /**
   * @param employeeToActivityFilter Object Used to List EmployeeToActivity
   * @param securityContext
   * @return PaginationResponse<EmployeeToActivity> containing paging information for
   *     EmployeeToActivity
   */
  public PaginationResponse<EmployeeToActivity> getAllEmployeeToActivities(
      EmployeeToActivityFilter employeeToActivityFilter, UserSecurityContext securityContext) {
    List<EmployeeToActivity> list =
        listAllEmployeeToActivities(employeeToActivityFilter, securityContext);
    long count =
        this.repository.countAllEmployeeToActivities(employeeToActivityFilter, securityContext);
    return new PaginationResponse<>(list, employeeToActivityFilter.getPageSize(), count);
  }

  /**
   * @param employeeToActivityFilter Object Used to List EmployeeToActivity
   * @param securityContext
   * @return List of EmployeeToActivity
   */
  public List<EmployeeToActivity> listAllEmployeeToActivities(
      EmployeeToActivityFilter employeeToActivityFilter, UserSecurityContext securityContext) {
    return this.repository.listAllEmployeeToActivities(employeeToActivityFilter, securityContext);
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
