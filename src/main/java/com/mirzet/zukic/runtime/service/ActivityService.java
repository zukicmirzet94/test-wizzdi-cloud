package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.ActivityRepository;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.request.ActivityCreate;
import com.mirzet.zukic.runtime.request.ActivityFilter;
import com.mirzet.zukic.runtime.request.ActivityUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityService {

  @Autowired private ActivityRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param activityCreate Object Used to Create Activity
   * @param securityContext
   * @return created Activity
   */
  public Activity createActivity(
      ActivityCreate activityCreate, UserSecurityContext securityContext) {
    Activity activity = createActivityNoMerge(activityCreate, securityContext);
    this.repository.merge(activity);
    return activity;
  }

  /**
   * @param activityCreate Object Used to Create Activity
   * @param securityContext
   * @return created Activity unmerged
   */
  public Activity createActivityNoMerge(
      ActivityCreate activityCreate, UserSecurityContext securityContext) {
    Activity activity = new Activity();
    activity.setId(UUID.randomUUID().toString());
    updateActivityNoMerge(activity, activityCreate);

    return activity;
  }

  /**
   * @param activityCreate Object Used to Create Activity
   * @param activity
   * @return if activity was updated
   */
  public boolean updateActivityNoMerge(Activity activity, ActivityCreate activityCreate) {
    boolean update = basicService.updateBasicNoMerge(activity, activityCreate);

    if (activityCreate.getActualEndDate() != null
        && (!activityCreate.getActualEndDate().equals(activity.getActualEndDate()))) {
      activity.setActualEndDate(activityCreate.getActualEndDate());
      update = true;
    }

    if (activityCreate.getPriority() != null
        && (!activityCreate.getPriority().equals(activity.getPriority()))) {
      activity.setPriority(activityCreate.getPriority());
      update = true;
    }

    if (activityCreate.getPlannedEndDate() != null
        && (!activityCreate.getPlannedEndDate().equals(activity.getPlannedEndDate()))) {
      activity.setPlannedEndDate(activityCreate.getPlannedEndDate());
      update = true;
    }

    if (activityCreate.getActualStartDate() != null
        && (!activityCreate.getActualStartDate().equals(activity.getActualStartDate()))) {
      activity.setActualStartDate(activityCreate.getActualStartDate());
      update = true;
    }

    if (activityCreate.getTask() != null
        && (activity.getTask() == null
            || !activityCreate.getTask().getId().equals(activity.getTask().getId()))) {
      activity.setTask(activityCreate.getTask());
      update = true;
    }

    if (activityCreate.getPlannedBudget() != null
        && (!activityCreate.getPlannedBudget().equals(activity.getPlannedBudget()))) {
      activity.setPlannedBudget(activityCreate.getPlannedBudget());
      update = true;
    }

    if (activityCreate.getPlannedStartDate() != null
        && (!activityCreate.getPlannedStartDate().equals(activity.getPlannedStartDate()))) {
      activity.setPlannedStartDate(activityCreate.getPlannedStartDate());
      update = true;
    }

    if (activityCreate.getActualBudget() != null
        && (!activityCreate.getActualBudget().equals(activity.getActualBudget()))) {
      activity.setActualBudget(activityCreate.getActualBudget());
      update = true;
    }

    return update;
  }

  /**
   * @param activityUpdate
   * @param securityContext
   * @return activity
   */
  public Activity updateActivity(
      ActivityUpdate activityUpdate, UserSecurityContext securityContext) {
    Activity activity = activityUpdate.getActivity();
    if (updateActivityNoMerge(activity, activityUpdate)) {
      this.repository.merge(activity);
    }
    return activity;
  }

  /**
   * @param activityFilter Object Used to List Activity
   * @param securityContext
   * @return PaginationResponse<Activity> containing paging information for Activity
   */
  public PaginationResponse<Activity> getAllActivities(
      ActivityFilter activityFilter, UserSecurityContext securityContext) {
    List<Activity> list = listAllActivities(activityFilter, securityContext);
    long count = this.repository.countAllActivities(activityFilter, securityContext);
    return new PaginationResponse<>(list, activityFilter.getPageSize(), count);
  }

  /**
   * @param activityFilter Object Used to List Activity
   * @param securityContext
   * @return List of Activity
   */
  public List<Activity> listAllActivities(
      ActivityFilter activityFilter, UserSecurityContext securityContext) {
    return this.repository.listAllActivities(activityFilter, securityContext);
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
