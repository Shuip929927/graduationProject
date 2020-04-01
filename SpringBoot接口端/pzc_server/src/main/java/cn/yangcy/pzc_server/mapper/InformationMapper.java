package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationMapper {

    //查
    @Select("SELECT * FROM tb_information WHERE id = #{id}")
    Information getInformationById(Integer id);

    //查全部
    @Select("SELECT * FROM tb_information WHERE isDelete = 0 ORDER BY createOn DESC")
    public List<Information> getAllInformationList();

    //增
    @Insert("insert into tb_information(type,title,author,createOn,content) " +
            "values(#{type},#{title},#{author},#{createOn},#{content})")
    public int addInformation(Information information);

    //删
    @Delete("delete from tb_information where id=#{id}")
    public int delete(Integer id);

    //Integer id, String type, String title, String author, String createOn,
    //String content, Integer isDelete, Integer hits
    //改
    @Update("update tb_information set type=#{information.type},title=#{information.title}," +
            "author=#{information.author},createOn=#{information.createOn},content=#{information.content}" +
            ",isDelete=#{information.isDelete},hits=#{information.hits} where" +
            " id=#{information.id}")
    public int update(@Param("information") Information information);
}
