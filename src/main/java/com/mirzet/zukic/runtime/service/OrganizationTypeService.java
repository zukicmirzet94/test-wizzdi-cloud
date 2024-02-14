package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.OrganizationTypeRepository;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.request.OrganizationTypeCreate;
import com.mirzet.zukic.runtime.request.OrganizationTypeFilter;
import com.mirzet.zukic.runtime.request.OrganizationTypeUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationTypeService {

  @Autowired private OrganizationTypeRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param organizationTypeCreate Object Used to Create OrganizationType
   * @param securityContext
   * @return created OrganizationType
   */
  public OrganizationType createOrganizationType(
      OrganizationTypeCreate organizationTypeCreate, UserSecurityContext securityContext) {
    OrganizationType organizationType =
        createOrganizationTypeNoMerge(organizationTypeCreate, securityContext);
    this.repository.merge(organizationType);
    return organizationType;
  }

  /**
   * @param organizationTypeCreate Object Used to Create OrganizationType
   * @param securityContext
   * @return created OrganizationType unmerged
   */
  public OrganizationType createOrganizationTypeNoMerge(
      OrganizationTypeCreate organizationTypeCreate, UserSecurityContext securityContext) {
    OrganizationType organizationType = new OrganizationType();
    organizationType.setId(UUID.randomUUID().toString());
    updateOrganizationTypeNoMerge(organizationType, organizationTypeCreate);

    return organizationType;
  }

  /**
   * @param organizationTypeCreate Object Used to Create OrganizationType
   * @param organizationType
   * @return if organizationType was updated
   */
  public boolean updateOrganizationTypeNoMerge(
      OrganizationType organizationType, OrganizationTypeCreate organizationTypeCreate) {
    boolean update = basicService.updateBasicNoMerge(organizationType, organizationTypeCreate);

    return update;
  }

  /**
   * @param organizationTypeUpdate
   * @param securityContext
   * @return organizationType
   */
  public OrganizationType updateOrganizationType(
      OrganizationTypeUpdate organizationTypeUpdate, UserSecurityContext securityContext) {
    OrganizationType organizationType = organizationTypeUpdate.getOrganizationType();
    if (updateOrganizationTypeNoMerge(organizationType, organizationTypeUpdate)) {
      this.repository.merge(organizationType);
    }
    return organizationType;
  }

  /**
   * @param organizationTypeFilter Object Used to List OrganizationType
   * @param securityContext
   * @return PaginationResponse<OrganizationType> containing paging information for OrganizationType
   */
  public PaginationResponse<OrganizationType> getAllOrganizationTypes(
      OrganizationTypeFilter organizationTypeFilter, UserSecurityContext securityContext) {
    List<OrganizationType> list = listAllOrganizationTypes(organizationTypeFilter, securityContext);
    long count = this.repository.countAllOrganizationTypes(organizationTypeFilter, securityContext);
    return new PaginationResponse<>(list, organizationTypeFilter.getPageSize(), count);
  }

  /**
   * @param organizationTypeFilter Object Used to List OrganizationType
   * @param securityContext
   * @return List of OrganizationType
   */
  public List<OrganizationType> listAllOrganizationTypes(
      OrganizationTypeFilter organizationTypeFilter, UserSecurityContext securityContext) {
    return this.repository.listAllOrganizationTypes(organizationTypeFilter, securityContext);
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
