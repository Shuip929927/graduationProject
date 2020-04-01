package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.ActivityEnroll;
import cn.yangcy.pzc_server.bean.OrganizationEnroll;

import java.util.List;

public interface ActivityEnrollService {

    //通过id查询
    ActivityEnroll getActivityEnrollById(Integer id);

    //通过UserId、activityId
    ActivityEnroll getActivityEnrollByUserIdAndActivityId(Integer userId,Integer activityId);

    //获取User 报名 activity的列表
    List<ActivityEnroll> getActivityEnrollByUserId(Integer userId);

    //获取activity 报名成功人员的列表
    List<ActivityEnroll> getActivityEnrollState2ByActivityId(Integer activityId);

    //获取activity 报名待审核人员的列表
    List<ActivityEnroll> getActivityEnrollState1ByActivityId(Integer activityId);

    //获取所有列表
    List<ActivityEnroll> getAllActivityEnroll();

    //增
    int add(ActivityEnroll activityEnroll);

    //删
    int delete(Integer id);

    //改
    int update(ActivityEnroll activityEnroll);
}
