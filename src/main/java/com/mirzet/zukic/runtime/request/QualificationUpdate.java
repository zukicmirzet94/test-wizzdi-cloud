package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Qualification */
@IdValid.List({
  @IdValid(
      targetField = "qualification",
      field = "id",
      fieldType = Qualification.class,
      groups = {Update.class})
})
public class QualificationUpdate extends QualificationCreate {

  private String id;

  @JsonIgnore private Qualification qualification;

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return QualificationUpdate
   */
  public <T extends QualificationUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return qualification
   */
  @JsonIgnore
  public Qualification getQualification() {
    return this.qualification;
  }

  /**
   * @param qualification qualification to set
   * @return QualificationUpdate
   */
  public <T extends QualificationUpdate> T setQualification(Qualification qualification) {
    this.qualification = qualification;
    return (T) this;
  }
}
