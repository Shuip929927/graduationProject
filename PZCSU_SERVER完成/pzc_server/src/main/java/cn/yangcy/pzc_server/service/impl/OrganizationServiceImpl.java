package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Organization;
import cn.yangcy.pzc_server.mapper.OrganizationMapper;
import cn.yangcy.pzc_server.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper mapper;

    @Override
    public Organization getOrganizationById(Integer id) {
        return mapper.getOrganizationById(id);
    }

    @Override
    public List<Organization> getOrganizationList() {
        return mapper.getAllOrganizationList();
    }

    @Override
    public List<Organization> getUserEnrollOrganizationListByUserId(int userId) {
        return mapper.getUserEnrollOrganizationListByUserId(userId);
    }

    @Override
    public int add(Organization organization) {
        return mapper.addOrganization(organization);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public int update(Organization organization) {
        return mapper.update(organization);
    }
}
