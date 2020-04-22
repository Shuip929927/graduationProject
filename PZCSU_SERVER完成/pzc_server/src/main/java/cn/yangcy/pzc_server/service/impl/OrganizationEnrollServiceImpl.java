package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.OrganizationEnroll;
import cn.yangcy.pzc_server.mapper.OrganizationEnrollMapper;
import cn.yangcy.pzc_server.service.OrganizationEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationEnrollServiceImpl  implements OrganizationEnrollService{

    @Autowired
    private OrganizationEnrollMapper mapper;

    @Override
    public OrganizationEnroll getOrganizationEnrollById(Integer id) {
        return mapper.getOrganizationEnrollById(id);
    }

    @Override
    public OrganizationEnroll getOrganizationEnrollByUserIdAndOrganizationId(Integer userId, Integer organizationId) {
        return mapper.getOrganizationEnrollByUserIdAndOrganizationId(userId,organizationId);
    }

    @Override
    public List<OrganizationEnroll> getOrganizationEnrollByUserId(Integer userId) {
        return mapper.getOrganizationEnrollByUserId(userId);
    }

    @Override
    public List<OrganizationEnroll> getOrganizationEnrollByOrganizationId(Integer organizationId) {
        return mapper.getOrganizationEnrollByOrganizationId(organizationId);
    }

    @Override
    public List<OrganizationEnroll> getAllOrganizationEnroll() {
        return mapper.getAllOrganizationEnroll();
    }

    @Override
    public int add(OrganizationEnroll organizationEnroll) {
        return mapper.add(organizationEnroll);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public int update(OrganizationEnroll organizationEnroll) {
        return mapper.update(organizationEnroll);
    }
}
