package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.RoleRepository;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.request.RoleCreate;
import com.mirzet.zukic.runtime.request.RoleFilter;
import com.mirzet.zukic.runtime.request.RoleUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleService {

  @Autowired private RoleRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param roleCreate Object Used to Create Role
   * @param securityContext
   * @return created Role
   */
  public Role createRole(RoleCreate roleCreate, UserSecurityContext securityContext) {
    Role role = createRoleNoMerge(roleCreate, securityContext);
    this.repository.merge(role);
    return role;
  }

  /**
   * @param roleCreate Object Used to Create Role
   * @param securityContext
   * @return created Role unmerged
   */
  public Role createRoleNoMerge(RoleCreate roleCreate, UserSecurityContext securityContext) {
    Role role = new Role();
    role.setId(UUID.randomUUID().toString());
    updateRoleNoMerge(role, roleCreate);

    return role;
  }

  /**
   * @param roleCreate Object Used to Create Role
   * @param role
   * @return if role was updated
   */
  public boolean updateRoleNoMerge(Role role, RoleCreate roleCreate) {
    boolean update = basicService.updateBasicNoMerge(role, roleCreate);

    return update;
  }

  /**
   * @param roleUpdate
   * @param securityContext
   * @return role
   */
  public Role updateRole(RoleUpdate roleUpdate, UserSecurityContext securityContext) {
    Role role = roleUpdate.getRole();
    if (updateRoleNoMerge(role, roleUpdate)) {
      this.repository.merge(role);
    }
    return role;
  }

  /**
   * @param roleFilter Object Used to List Role
   * @param securityContext
   * @return PaginationResponse<Role> containing paging information for Role
   */
  public PaginationResponse<Role> getAllRoles(
      RoleFilter roleFilter, UserSecurityContext securityContext) {
    List<Role> list = listAllRoles(roleFilter, securityContext);
    long count = this.repository.countAllRoles(roleFilter, securityContext);
    return new PaginationResponse<>(list, roleFilter.getPageSize(), count);
  }

  /**
   * @param roleFilter Object Used to List Role
   * @param securityContext
   * @return List of Role
   */
  public List<Role> listAllRoles(RoleFilter roleFilter, UserSecurityContext securityContext) {
    return this.repository.listAllRoles(roleFilter, securityContext);
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
