package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Organization;

import java.util.List;

public interface OrganizationService {

    //通过id查询
    Organization getOrganizationById(Integer id);

    //获取学生组织列表
    public List<Organization> getOrganizationList();

    //增
    public int add(Organization organization);

    //删
    public int delete(Integer id);

    //改
    public int update(Organization organization);
}
