package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    //查
    @Select("SELECT * FROM tb_department WHERE id = #{id}")
    Department getDepartmentById(Integer id);

    //查全部
    @Select("SELECT * FROM tb_department")
    public List<Department> getAllDepartmentList();

    //增
    @Insert("insert into tb_department(department_name) " +
            "values(#{departmentName})")
    public int addDepartment(Department department);

    //删
    @Delete("delete from tb_department where id=#{id}")
    public int delete(Integer id);

    //Integer id, String type, String title, String author, String createOn,
    //String content, Integer isDelete, Integer hits
    //改
    @Update("update tb_department set department_name=#{department.departmentName} where" +
            " id=#{department.id}")
    public int update(@Param("department") Department department);
}
