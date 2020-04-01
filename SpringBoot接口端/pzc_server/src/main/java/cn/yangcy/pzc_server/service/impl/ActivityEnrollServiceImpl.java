package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.ActivityEnroll;
import cn.yangcy.pzc_server.mapper.ActivityEnrollMapper;
import cn.yangcy.pzc_server.service.ActivityEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityEnrollServiceImpl implements ActivityEnrollService {

    @Autowired
    private ActivityEnrollMapper mapper;

    @Override
    public ActivityEnroll getActivityEnrollById(Integer id) {
        return mapper.getActivityEnrollById(id);
    }

    @Override
    public ActivityEnroll getActivityEnrollByUserIdAndActivityId(Integer userId, Integer activityId) {
        return mapper.getActivityEnrollByUserIdAndActivityId(userId,activityId);
    }

    @Override
    public List<ActivityEnroll> getActivityEnrollByUserId(Integer userId) {
        return mapper.getActivityEnrollByUserId(userId);
    }

    @Override
    public List<ActivityEnroll> getActivityEnrollState2ByActivityId(Integer activityId) {
        return mapper.getActivityEnrollState2ByActivityId(activityId);
    }

    @Override
    public List<ActivityEnroll> getActivityEnrollState1ByActivityId(Integer activityId) {
        return mapper.getActivityEnrollState1ByActivityId(activityId);
    }

    @Override
    public List<ActivityEnroll> getAllActivityEnroll() {
        return mapper.getAllActivityEnroll();
    }

    @Override
    public int add(ActivityEnroll activityEnroll) {
        return mapper.add(activityEnroll);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public int update(ActivityEnroll activityEnroll) {
        return mapper.update(activityEnroll);
    }
}
