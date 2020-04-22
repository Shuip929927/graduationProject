package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Admin;

import java.util.List;

public interface AdminService {

    int addAdmin(Admin admin);

    int deleteAdmin(String account);

    int updateAdmin(Admin admin);

    Admin getAdminByAccount(String account);

    List<Admin> getAllAdmin();
}
