package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update DependendTaskToMainTask */
@IdValid.List({
  @IdValid(
      targetField = "dependendTaskToMainTask",
      field = "id",
      fieldType = DependendTaskToMainTask.class,
      groups = {Update.class})
})
public class DependendTaskToMainTaskUpdate extends DependendTaskToMainTaskCreate {

  @JsonIgnore private DependendTaskToMainTask dependendTaskToMainTask;

  private String id;

  /**
   * @return dependendTaskToMainTask
   */
  @JsonIgnore
  public DependendTaskToMainTask getDependendTaskToMainTask() {
    return this.dependendTaskToMainTask;
  }

  /**
   * @param dependendTaskToMainTask dependendTaskToMainTask to set
   * @return DependendTaskToMainTaskUpdate
   */
  public <T extends DependendTaskToMainTaskUpdate> T setDependendTaskToMainTask(
      DependendTaskToMainTask dependendTaskToMainTask) {
    this.dependendTaskToMainTask = dependendTaskToMainTask;
    return (T) this;
  }

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return DependendTaskToMainTaskUpdate
   */
  public <T extends DependendTaskToMainTaskUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
