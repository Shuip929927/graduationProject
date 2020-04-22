package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.*;

import java.util.List;

public interface BackGroundService {

    //后台增加学生组织报名信息
    int addOeBG(OrganizationEnroll organizationEnroll);

    //后台查询学生组织人员
    List<BackGroundOrgMember> getOrgMemberListBG(String organization_enroll_state, String user_id, String organization_id);

    //后台查所有学生活动
    List<Activity> getAllActivityBG(String organization_id, String activity_state);

    //后台增加活动报名信息
    int addAeBG(ActivityEnroll activityEnroll);

    //后台查询活动报名信息
    List<BackGroundActMember> getActMemberListBG(String activity_enroll_state, String user_id, String activity_id);

    //后台查全部用户
    List<User> getAllUserBG(String account, String gender, String departmentId);

    //后台查全部通知信息
    List<Information> getAllInformationListBG(String id, String type, String isDelete);

    //后台查询专业
    List<Major> getMajorListBG(String department_id);
}
