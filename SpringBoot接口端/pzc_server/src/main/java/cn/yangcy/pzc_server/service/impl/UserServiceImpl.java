package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.User;
import cn.yangcy.pzc_server.mapper.UserMapper;
import cn.yangcy.pzc_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

   //Autowired:把配置好的Bean拿来用，完成属性、方法的组装
   @Autowired
   private UserMapper userMapper;


    @Override
    public User getUserByAccount(Integer account) {
        return userMapper.getUserByAccount(account);
    }

//    @Override
//    public List<User> getUserListByAccounts(List<Integer> accounts) {
//        return userMapper.getUserListByAccounts(accounts);
//    }

    @Override
    public List<User> getActivityEnrollState2UserList(Integer activityId) {
        return userMapper.getActivityEnrollState2UserList(activityId);
    }

    @Override
    public List<User> getActivityEnrollState1UserList(Integer activityId) {
        return userMapper.getActivityEnrollState1UserList(activityId);
    }

    @Override
    public List<User> getOrganizationEnrollState2UserList(Integer organizationId) {
        return userMapper.getOrganizationEnrollState2UserList(organizationId);
    }

    @Override
    public List<User> getOrganizationEnrollState1UserList(Integer organizationId) {
        return userMapper.getOrganizationEnrollState1UserList(organizationId);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getAllUserList();
    }

    @Override
    public int add(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int delete(Integer account) {
        return userMapper.delete(account);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }
}
