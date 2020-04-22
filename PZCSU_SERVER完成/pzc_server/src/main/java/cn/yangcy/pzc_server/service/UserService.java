package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.BackGroundActMember;
import cn.yangcy.pzc_server.bean.BackGroundOrgMember;
import cn.yangcy.pzc_server.bean.User;

import java.util.List;

public interface UserService {

    //通过id查询
    User getUserByAccount(Integer account);

    //通过Id列表查询
//    List<User> getUserListByAccounts(@Param("accounts") List<Integer> accounts);

    //获取活动报名状态为2的用户列表
    List<User> getActivityEnrollState2UserList(Integer activityId);

    //获取活动报名状态为1的用户列表
    List<User> getActivityEnrollState1UserList(Integer activityId);

    //获取学生组织报名状态为2的用户列表
    List<User> getOrganizationEnrollState2UserList(Integer organizationId);

    //获取学生组织报名状态为1的用户列表
    List<User> getOrganizationEnrollState1UserList(Integer organizationId);

    //获取用户列表
    List<User> getUserList();

    //增
    public int add(User user);

    //删
    public int delete(Integer account);

    //改
    public int update(User user);

}
