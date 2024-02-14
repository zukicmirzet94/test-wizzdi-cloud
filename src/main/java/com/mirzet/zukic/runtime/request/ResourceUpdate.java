package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Resource */
@IdValid.List({
  @IdValid(
      targetField = "resource",
      field = "id",
      fieldType = Resource.class,
      groups = {Update.class})
})
public class ResourceUpdate extends ResourceCreate {

  private String id;

  @JsonIgnore private Resource resource;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return ResourceUpdate
   */
  public <T extends ResourceUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return resource
   */
  @JsonIgnore
  public Resource getResource() {
    return this.resource;
  }

  /**
   * @param resource resource to set
   * @return ResourceUpdate
   */
  public <T extends ResourceUpdate> T setResource(Resource resource) {
    this.resource = resource;
    return (T) this;
  }
}
