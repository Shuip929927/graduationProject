package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.User;

import java.util.List;

public interface UserService {

    //通过id查询
    User getUserByAccount(Integer account);

    //获取用户列表
    public List<User> getUserList();

    //增
    public int add(User user);

    //删
    public int delete(Integer account);

    //改
    public int update(User user);
}
