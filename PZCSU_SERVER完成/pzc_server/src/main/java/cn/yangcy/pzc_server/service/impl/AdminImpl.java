package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Admin;
import cn.yangcy.pzc_server.mapper.AdminMapper;
import cn.yangcy.pzc_server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminImpl implements AdminService {

    @Autowired
    private AdminMapper mapper;

    @Override
    public int addAdmin(Admin admin) {
        return mapper.addAdmin(admin);
    }

    @Override
    public int deleteAdmin(String account) {
        return mapper.deleteAdmin(account);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return mapper.updateAdmin(admin);
    }

    @Override
    public Admin getAdminByAccount(String account) {
        return mapper.getAdminByAccount(account);
    }

    @Override
    public List<Admin> getAllAdmin() {
        return mapper.getAllAdmin();
    }
}
