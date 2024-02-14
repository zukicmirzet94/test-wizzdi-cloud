package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.OrganizationRepository;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.request.OrganizationCreate;
import com.mirzet.zukic.runtime.request.OrganizationFilter;
import com.mirzet.zukic.runtime.request.OrganizationUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationService {

  @Autowired private OrganizationRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param organizationCreate Object Used to Create Organization
   * @param securityContext
   * @return created Organization
   */
  public Organization createOrganization(
      OrganizationCreate organizationCreate, UserSecurityContext securityContext) {
    Organization organization = createOrganizationNoMerge(organizationCreate, securityContext);
    this.repository.merge(organization);
    return organization;
  }

  /**
   * @param organizationCreate Object Used to Create Organization
   * @param securityContext
   * @return created Organization unmerged
   */
  public Organization createOrganizationNoMerge(
      OrganizationCreate organizationCreate, UserSecurityContext securityContext) {
    Organization organization = new Organization();
    organization.setId(UUID.randomUUID().toString());
    updateOrganizationNoMerge(organization, organizationCreate);

    return organization;
  }

  /**
   * @param organizationCreate Object Used to Create Organization
   * @param organization
   * @return if organization was updated
   */
  public boolean updateOrganizationNoMerge(
      Organization organization, OrganizationCreate organizationCreate) {
    boolean update = basicService.updateBasicNoMerge(organization, organizationCreate);

    if (organizationCreate.getCountry() != null
        && (!organizationCreate.getCountry().equals(organization.getCountry()))) {
      organization.setCountry(organizationCreate.getCountry());
      update = true;
    }

    if (organizationCreate.getOrganizationType() != null
        && (organization.getOrganizationType() == null
            || !organizationCreate
                .getOrganizationType()
                .getId()
                .equals(organization.getOrganizationType().getId()))) {
      organization.setOrganizationType(organizationCreate.getOrganizationType());
      update = true;
    }

    if (organizationCreate.getType() != null
        && (!organizationCreate.getType().equals(organization.getType()))) {
      organization.setType(organizationCreate.getType());
      update = true;
    }

    return update;
  }

  /**
   * @param organizationUpdate
   * @param securityContext
   * @return organization
   */
  public Organization updateOrganization(
      OrganizationUpdate organizationUpdate, UserSecurityContext securityContext) {
    Organization organization = organizationUpdate.getOrganization();
    if (updateOrganizationNoMerge(organization, organizationUpdate)) {
      this.repository.merge(organization);
    }
    return organization;
  }

  /**
   * @param organizationFilter Object Used to List Organization
   * @param securityContext
   * @return PaginationResponse<Organization> containing paging information for Organization
   */
  public PaginationResponse<Organization> getAllOrganizations(
      OrganizationFilter organizationFilter, UserSecurityContext securityContext) {
    List<Organization> list = listAllOrganizations(organizationFilter, securityContext);
    long count = this.repository.countAllOrganizations(organizationFilter, securityContext);
    return new PaginationResponse<>(list, organizationFilter.getPageSize(), count);
  }

  /**
   * @param organizationFilter Object Used to List Organization
   * @param securityContext
   * @return List of Organization
   */
  public List<Organization> listAllOrganizations(
      OrganizationFilter organizationFilter, UserSecurityContext securityContext) {
    return this.repository.listAllOrganizations(organizationFilter, securityContext);
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
