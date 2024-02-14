package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.BasicRepository;
import com.mirzet.zukic.runtime.model.Basic;
import com.mirzet.zukic.runtime.request.BasicCreate;
import com.mirzet.zukic.runtime.request.BasicFilter;
import com.mirzet.zukic.runtime.request.BasicUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicService {

  @Autowired private BasicRepository repository;

  /**
   * @param basicCreate Object Used to Create Basic
   * @param securityContext
   * @return created Basic
   */
  public Basic createBasic(BasicCreate basicCreate, UserSecurityContext securityContext) {
    Basic basic = createBasicNoMerge(basicCreate, securityContext);
    this.repository.merge(basic);
    return basic;
  }

  /**
   * @param basicCreate Object Used to Create Basic
   * @param securityContext
   * @return created Basic unmerged
   */
  public Basic createBasicNoMerge(BasicCreate basicCreate, UserSecurityContext securityContext) {
    Basic basic = new Basic();
    basic.setId(UUID.randomUUID().toString());
    updateBasicNoMerge(basic, basicCreate);

    basic.setCreationDate(OffsetDateTime.now());

    return basic;
  }

  /**
   * @param basicCreate Object Used to Create Basic
   * @param basic
   * @return if basic was updated
   */
  public boolean updateBasicNoMerge(Basic basic, BasicCreate basicCreate) {
    boolean update = false;

    if (basicCreate.getCreationDate() != null
        && (!basicCreate.getCreationDate().equals(basic.getCreationDate()))) {
      basic.setCreationDate(basicCreate.getCreationDate());
      update = true;
    }

    if (basicCreate.getName() != null && (!basicCreate.getName().equals(basic.getName()))) {
      basic.setName(basicCreate.getName());
      update = true;
    }

    if (basicCreate.getUpdateDate() != null
        && (!basicCreate.getUpdateDate().equals(basic.getUpdateDate()))) {
      basic.setUpdateDate(basicCreate.getUpdateDate());
      update = true;
    }

    if (basicCreate.getDescription() != null
        && (!basicCreate.getDescription().equals(basic.getDescription()))) {
      basic.setDescription(basicCreate.getDescription());
      update = true;
    }

    if (update) {
      basic.setUpdateDate(OffsetDateTime.now());
    }

    return update;
  }

  /**
   * @param basicUpdate
   * @param securityContext
   * @return basic
   */
  public Basic updateBasic(BasicUpdate basicUpdate, UserSecurityContext securityContext) {
    Basic basic = basicUpdate.getBasic();
    if (updateBasicNoMerge(basic, basicUpdate)) {
      this.repository.merge(basic);
    }
    return basic;
  }

  /**
   * @param basicFilter Object Used to List Basic
   * @param securityContext
   * @return PaginationResponse<Basic> containing paging information for Basic
   */
  public PaginationResponse<Basic> getAllBasics(
      BasicFilter basicFilter, UserSecurityContext securityContext) {
    List<Basic> list = listAllBasics(basicFilter, securityContext);
    long count = this.repository.countAllBasics(basicFilter, securityContext);
    return new PaginationResponse<>(list, basicFilter.getPageSize(), count);
  }

  /**
   * @param basicFilter Object Used to List Basic
   * @param securityContext
   * @return List of Basic
   */
  public List<Basic> listAllBasics(BasicFilter basicFilter, UserSecurityContext securityContext) {
    return this.repository.listAllBasics(basicFilter, securityContext);
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
