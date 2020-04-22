package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Department;
import cn.yangcy.pzc_server.mapper.DepartmentMapper;
import cn.yangcy.pzc_server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public Department getDepartmentById(Integer id) {
        return departmentMapper.getDepartmentById(id);
    }

    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.getAllDepartmentList();
    }

    @Override
    public int add(Department department) {
        return departmentMapper.addDepartment(department);
    }

    @Override
    public int delete(Integer id) {
        return departmentMapper.delete(id);
    }

    @Override
    public int update(Department department) {
        return departmentMapper.update(department);
    }
}
