package cn.yangcy.pzc_server.service;

import cn.yangcy.pzc_server.bean.Information;

import java.util.List;

public interface InformationService {

    //通过id查询
    Information getInformationById(Integer id);

    //获取用户列表
    public List<Information> getInformationList();

    //增
    public int add(Information information);

    //删
    public int delete(Integer id);

    //改
    public int update(Information information);

}
