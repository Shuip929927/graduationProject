package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Major;
import cn.yangcy.pzc_server.mapper.MajorMapper;
import cn.yangcy.pzc_server.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper mapper;

    @Override
    public Major getMajorById(Integer id) {
        return mapper.getMajorById(id);
    }

    @Override
    public List<Major> getDepartmentMajorList(Integer department_id) {
        return mapper.getMajorByDepartmentId(department_id);
    }

    @Override
    public List<Major> getMajorList() {
        return mapper.getAllMajor();
    }

    @Override
    public int add(Major major) {
        return mapper.add(major);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public int update(Major major) {
        return mapper.update(major);
    }
}
