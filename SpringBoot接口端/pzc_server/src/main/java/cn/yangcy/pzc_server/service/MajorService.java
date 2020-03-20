package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Major;

import java.util.List;

public interface MajorService {

    //通过id查询
    Major getMajorById(Integer id);

    //获取某系的专业列表
    public List<Major> getDepartmentMajorList(Integer department_id);

    //获取专业列表
    public List<Major> getMajorList();

    //增
    public int add(Major major);

    //删
    public int delete(Integer id);

    //改
    public int update(Major information);
}
