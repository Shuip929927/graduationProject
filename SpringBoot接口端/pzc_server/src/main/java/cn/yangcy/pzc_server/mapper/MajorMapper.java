package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MajorMapper {

    @Select("SELECT * FROM tb_major WHERE id = #{id}")
    Major getMajorById(Integer id);

    @Select("SELECT * FROM tb_major WHERE department_id = #{department_id}")
    List<Major> getMajorByDepartmentId(Integer department_id);

    @Select("SELECT * FROM tb_major")
    List<Major> getAllMajor();

    @Insert("INSERT INTO tb_major(department_id,major_name) VALUES" +
            "(#{department_id},#{major_name})")
    int add(Major major);

    @Delete("DELETE FROM tb_major WHERE id = #{id}")
    int delete(Integer id);

    @Update("UPDATE tb_major SET department_id = #{department_id},major_name = #{major_name}" +
            "WHERE id = #{id}")
    int update(Major major);
}
