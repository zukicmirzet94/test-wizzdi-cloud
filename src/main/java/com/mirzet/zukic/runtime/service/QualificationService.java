package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.QualificationRepository;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.request.QualificationCreate;
import com.mirzet.zukic.runtime.request.QualificationFilter;
import com.mirzet.zukic.runtime.request.QualificationUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QualificationService {

  @Autowired private QualificationRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param qualificationCreate Object Used to Create Qualification
   * @param securityContext
   * @return created Qualification
   */
  public Qualification createQualification(
      QualificationCreate qualificationCreate, UserSecurityContext securityContext) {
    Qualification qualification = createQualificationNoMerge(qualificationCreate, securityContext);
    this.repository.merge(qualification);
    return qualification;
  }

  /**
   * @param qualificationCreate Object Used to Create Qualification
   * @param securityContext
   * @return created Qualification unmerged
   */
  public Qualification createQualificationNoMerge(
      QualificationCreate qualificationCreate, UserSecurityContext securityContext) {
    Qualification qualification = new Qualification();
    qualification.setId(UUID.randomUUID().toString());
    updateQualificationNoMerge(qualification, qualificationCreate);

    return qualification;
  }

  /**
   * @param qualificationCreate Object Used to Create Qualification
   * @param qualification
   * @return if qualification was updated
   */
  public boolean updateQualificationNoMerge(
      Qualification qualification, QualificationCreate qualificationCreate) {
    boolean update = basicService.updateBasicNoMerge(qualification, qualificationCreate);

    return update;
  }

  /**
   * @param qualificationUpdate
   * @param securityContext
   * @return qualification
   */
  public Qualification updateQualification(
      QualificationUpdate qualificationUpdate, UserSecurityContext securityContext) {
    Qualification qualification = qualificationUpdate.getQualification();
    if (updateQualificationNoMerge(qualification, qualificationUpdate)) {
      this.repository.merge(qualification);
    }
    return qualification;
  }

  /**
   * @param qualificationFilter Object Used to List Qualification
   * @param securityContext
   * @return PaginationResponse<Qualification> containing paging information for Qualification
   */
  public PaginationResponse<Qualification> getAllQualifications(
      QualificationFilter qualificationFilter, UserSecurityContext securityContext) {
    List<Qualification> list = listAllQualifications(qualificationFilter, securityContext);
    long count = this.repository.countAllQualifications(qualificationFilter, securityContext);
    return new PaginationResponse<>(list, qualificationFilter.getPageSize(), count);
  }

  /**
   * @param qualificationFilter Object Used to List Qualification
   * @param securityContext
   * @return List of Qualification
   */
  public List<Qualification> listAllQualifications(
      QualificationFilter qualificationFilter, UserSecurityContext securityContext) {
    return this.repository.listAllQualifications(qualificationFilter, securityContext);
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
