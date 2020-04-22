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
    @Select("SELECT * FROM tb_activities WHERE isDelete = 0 ORDER BY create_time DESC")
    List<Activity> getAllStudentActivityList();

    //查找用户报名成功的活动
    @Select("SELECT * FROM tb_activities WHERE isDelete = 0 AND id IN " +
            "(SELECT activity_id from tb_activities_enroll where activity_enroll_state = 2 AND user_id = #{userId})")
    List<Activity> getUserEnrollState2ActivityListByUserId(Integer userId);

    //查找该用户为部门管理员时所在部门举办的活动
    @Select("SELECT * FROM tb_activities " +
            "WHERE organization_id = (SELECT id FROM tb_organization WHERE person_id = #{userId}) " +
            "ORDER BY create_time DESC")
   List<Activity> getOrganizationHoldActivityByUserId(Integer userId);

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
