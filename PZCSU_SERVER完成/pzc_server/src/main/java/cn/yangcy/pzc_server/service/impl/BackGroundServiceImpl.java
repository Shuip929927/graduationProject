package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.*;
import cn.yangcy.pzc_server.mapper.BackGroundMapper;
import cn.yangcy.pzc_server.service.BackGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackGroundServiceImpl implements BackGroundService {

    @Autowired
    private BackGroundMapper mapper;


    @Override
    public int addOeBG(OrganizationEnroll organizationEnroll) {
        return mapper.addOeBG(organizationEnroll);
    }

    @Override
    public List<BackGroundOrgMember> getOrgMemberListBG(String organization_enroll_state, String user_id, String organization_id) {
        return mapper.getOrgMemberListBG(organization_enroll_state,user_id,organization_id);
    }

    @Override
    public List<Activity> getAllActivityBG(String organization_id, String activity_state) {
        return mapper.getAllActivityBG(organization_id,activity_state);
    }

    @Override
    public int addAeBG(ActivityEnroll activityEnroll) {
        return mapper.addAeBG(activityEnroll);
    }

    @Override
    public List<BackGroundActMember> getActMemberListBG(String activity_enroll_state, String user_id, String activity_id) {
        return mapper.getActMemberListBG(activity_enroll_state,user_id,activity_id);
    }

    @Override
    public List<User> getAllUserBG(String account, String gender, String departmentId) {
        return mapper.getAllUserBG(account,gender,departmentId);
    }

    @Override
    public List<Information> getAllInformationListBG(String id, String type, String isDelete) {
        return mapper.getAllInformationListBG(id,type,isDelete);
    }

    @Override
    public List<Major> getMajorListBG(String department_id) {
        return mapper.getMajorListBG(department_id);
    }

}
