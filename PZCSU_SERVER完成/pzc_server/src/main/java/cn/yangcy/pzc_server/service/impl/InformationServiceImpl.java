package cn.yangcy.pzc_server.service.impl;

import cn.yangcy.pzc_server.bean.Information;
import cn.yangcy.pzc_server.mapper.InformationMapper;
import cn.yangcy.pzc_server.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationMapper informationMapper;

    @Override
    public Information getInformationById(Integer id) {
        return informationMapper.getInformationById(id);
    }

    @Override
    public List<Information> getInformationList() {
        return informationMapper.getAllInformationList();
    }

    @Override
    public int add(Information information) {
        return informationMapper.addInformation(information);
    }

    @Override
    public int delete(Integer id) {
        return informationMapper.delete(id);
    }

    @Override
    public int update(Information information) {
        return informationMapper.update(information);
    }
}
