package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Activity;
import cn.yangcy.pzc_server.mapper.ActivityMapper;
import cn.yangcy.pzc_server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper mapper;

    @Override
    public Activity getActivityById(Integer id) {
        return mapper.getStudentActivityById(id);
    }

    @Override
    public List<Activity> getActivitiesList() {
        return mapper.getAllStudentActivityList();
    }

    @Override
    public int add(Activity activity) {
        return mapper.addStudentActivity(activity);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public int update(Activity activity) {
        return mapper.update(activity);
    }
}
