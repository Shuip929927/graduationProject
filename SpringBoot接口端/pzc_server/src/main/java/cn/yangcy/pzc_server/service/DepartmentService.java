package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Department;
import cn.yangcy.pzc_server.bean.User;

import java.util.List;

public interface DepartmentService {

    //通过id查询
    Department getDepartmentById(Integer id);

    //获取用户列表
    public List<Department> getDepartmentList();

    //增
    public int add(Department department);

    //删
    public int delete(Integer id);

    //改
    public int update(Department department);
}
