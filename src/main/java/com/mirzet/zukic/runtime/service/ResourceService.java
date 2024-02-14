package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.ResourceRepository;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.request.ResourceCreate;
import com.mirzet.zukic.runtime.request.ResourceFilter;
import com.mirzet.zukic.runtime.request.ResourceUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceService {

  @Autowired private ResourceRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param resourceCreate Object Used to Create Resource
   * @param securityContext
   * @return created Resource
   */
  public Resource createResource(
      ResourceCreate resourceCreate, UserSecurityContext securityContext) {
    Resource resource = createResourceNoMerge(resourceCreate, securityContext);
    this.repository.merge(resource);
    return resource;
  }

  /**
   * @param resourceCreate Object Used to Create Resource
   * @param securityContext
   * @return created Resource unmerged
   */
  public Resource createResourceNoMerge(
      ResourceCreate resourceCreate, UserSecurityContext securityContext) {
    Resource resource = new Resource();
    resource.setId(UUID.randomUUID().toString());
    updateResourceNoMerge(resource, resourceCreate);

    return resource;
  }

  /**
   * @param resourceCreate Object Used to Create Resource
   * @param resource
   * @return if resource was updated
   */
  public boolean updateResourceNoMerge(Resource resource, ResourceCreate resourceCreate) {
    boolean update = basicService.updateBasicNoMerge(resource, resourceCreate);

    return update;
  }

  /**
   * @param resourceUpdate
   * @param securityContext
   * @return resource
   */
  public Resource updateResource(
      ResourceUpdate resourceUpdate, UserSecurityContext securityContext) {
    Resource resource = resourceUpdate.getResource();
    if (updateResourceNoMerge(resource, resourceUpdate)) {
      this.repository.merge(resource);
    }
    return resource;
  }

  /**
   * @param resourceFilter Object Used to List Resource
   * @param securityContext
   * @return PaginationResponse<Resource> containing paging information for Resource
   */
  public PaginationResponse<Resource> getAllResources(
      ResourceFilter resourceFilter, UserSecurityContext securityContext) {
    List<Resource> list = listAllResources(resourceFilter, securityContext);
    long count = this.repository.countAllResources(resourceFilter, securityContext);
    return new PaginationResponse<>(list, resourceFilter.getPageSize(), count);
  }

  /**
   * @param resourceFilter Object Used to List Resource
   * @param securityContext
   * @return List of Resource
   */
  public List<Resource> listAllResources(
      ResourceFilter resourceFilter, UserSecurityContext securityContext) {
    return this.repository.listAllResources(resourceFilter, securityContext);
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
