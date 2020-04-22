package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Activity;

import java.util.List;

public interface ActivityService {

    Activity getActivityById(Integer id);

    List<Activity> getActivitiesList();

    List<Activity> getUserEnrollState2ActivityListByUserId(Integer userId);

    List<Activity> getOrganizationHoldActivityByUserId(Integer userId);

    int add(Activity activity);

    int delete(Integer id);

    int update(Activity activity);
}
