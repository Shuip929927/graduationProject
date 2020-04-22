package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.OrganizationEnroll;

import java.util.List;

public interface OrganizationEnrollService {

    //通过id查询
    OrganizationEnroll getOrganizationEnrollById(Integer id);

    //通过UserId、organizationId查询
    OrganizationEnroll getOrganizationEnrollByUserIdAndOrganizationId(Integer userId,Integer organizationId);

    //获取User 报名 organization的列表
    List<OrganizationEnroll> getOrganizationEnrollByUserId(Integer userId);

    //获取organization 报名人员的列表
    List<OrganizationEnroll> getOrganizationEnrollByOrganizationId(Integer organizationId);

    //获取所有列表
    List<OrganizationEnroll> getAllOrganizationEnroll();

    //增
    int add(OrganizationEnroll organizationEnroll);

    //删
    int delete(Integer id);

    //改
    int update(OrganizationEnroll organizationEnroll);
}
