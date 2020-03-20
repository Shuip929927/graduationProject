package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Activity;

import java.util.List;

public interface ActivityService {

    public Activity getActivityById(Integer id);

    public List<Activity> getActivitiesList();

    public int add(Activity activity);

    public int delete(Integer id);

    public int update(Activity activity);
}
