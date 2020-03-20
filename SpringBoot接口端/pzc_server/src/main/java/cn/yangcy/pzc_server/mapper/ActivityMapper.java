package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {
    //查
    @Select("SELECT * FROM tb_activities WHERE id = #{id}")
    Activity getStudentActivityById(Integer id);

    //查全部
    @Select("SELECT * FROM tb_activities")
    public List<Activity> getAllStudentActivityList();

    //增
    @Insert("insert into tb_activities(organization_id,activity_name," +
            "create_time,start_time,description,activity_state,isDelete)" +
            "values(#{organization_id},#{activity_name},#{create_time},#{start_time},#{description},#{activity_state},#{isDelete})")
    public int addStudentActivity(Activity activity);

    //删
    @Delete("delete from tb_activities where id=#{id}")
    public int delete(Integer id);

    //改
//    @Update("update tb_activities set organization_id=#{activity.organization_id},organization_name=#{activity.organizationName}," +
////            "activity_name=#{activity.name},activity_create_time=#{activity.createOn},activity_start_time=#{activity.startTime}" +
////            ",activity_description=#{activity.description},activity_state=#{activity.state},isDelete=#{activity.isDelete} where" +
////            " account=#{activity.account}")
////    public int update(@Param("student") Activity activity);
    @Update("update tb_activities set organization_id=#{organization_id}," +
            "activity_name=#{activity_name},create_time=#{create_time},start_time=#{start_time}" +
            ",description=#{description},activity_state=#{activity_state},isDelete=#{isDelete} where" +
            " id=#{id}")
    public int update(Activity activity);
}
