package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.StudentActivity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentActivityMapper {
    //查
    @Select("SELECT * FROM tb_activities WHERE id = #{id}")
    StudentActivity getStudentActivityById(Integer id);

    //查全部
    @Select("SELECT * FROM tb_activities")
    public List<StudentActivity> getAllStudentActivityList();

    //增
    @Insert("insert into tb_activities(organization_id,organization_name,activity_name," +
            "activity_create_time,activity_start_time,activity_description,activity_state,isDelete)" +
            "values(#{organizationId},#{organizationName},#{name},#{createOn},#{startTime},#{description},#{state},#{isDelete})")
    public int addStudentActivity(StudentActivity activity);

    //删
    @Delete("delete from tb_activities where id=#{id}")
    public int delete(Integer id);

    //改
    @Update("update tb_activities set organization_id=#{activity.organizationId},organization_name=#{activity.organizationName}," +
            "activity_name=#{activity.name},activity_create_time=#{activity.createOn},activity_start_time=#{activity.startTime}" +
            ",activity_description=#{activity.description},activity_state=#{activity.state},isDelete=#{activity.isDelete} where" +
            " account=#{activity.account}")
    public int update(@Param("student") StudentActivity activity);
}
