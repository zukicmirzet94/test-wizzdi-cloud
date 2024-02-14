package com.mirzet.zukic.runtime.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.validation.IdValid;
import com.mirzet.zukic.runtime.validation.Update;

/** Object Used to Update Activity */
@IdValid.List({
  @IdValid(
      targetField = "activity",
      field = "id",
      fieldType = Activity.class,
      groups = {Update.class})
})
public class ActivityUpdate extends ActivityCreate {

  @JsonIgnore private Activity activity;

  private String id;

  /**
   * @return activity
   */
  @JsonIgnore
  public Activity getActivity() {
    return this.activity;
  }

  /**
   * @param activity activity to set
   * @return ActivityUpdate
   */
  public <T extends ActivityUpdate> T setActivity(Activity activity) {
    this.activity = activity;
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
   * @return ActivityUpdate
   */
  public <T extends ActivityUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
