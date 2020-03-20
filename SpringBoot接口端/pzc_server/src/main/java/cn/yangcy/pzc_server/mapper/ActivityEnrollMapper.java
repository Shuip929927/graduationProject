package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.ActivityEnroll;
import cn.yangcy.pzc_server.bean.OrganizationEnroll;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityEnrollMapper {

    @Select("SELECT * FROM tb_activities_enroll WHERE id = #{id}")
    ActivityEnroll getActivityEnrollById(Integer id);

    @Select("SELECT * FROM tb_activities_enroll WHERE user_id = #{user_id} and activity_id = #{activity_id} ")
    ActivityEnroll getActivityEnrollByUserIdAndActivityId(@Param("user_id") Integer user_id, @Param("activity_id") Integer activity_id);

    @Select("SELECT * FROM tb_activities_enroll WHERE user_id = #{user_id}")
    List<ActivityEnroll> getActivityEnrollByUserId(Integer user_id);

    @Select("SELECT * FROM tb_activities_enroll WHERE activity_id = #{activity_id}")
    List<ActivityEnroll> getActivityEnrollByActivityId(Integer activity_id);

    @Select("SELECT * FROM tb_activities_enroll")
    List<ActivityEnroll> getAllActivityEnroll();

    @Insert("INSERT INTO tb_activities_enroll(user_id,activity_id) VALUES (#{user_id},#{activity_id})")
    int add(ActivityEnroll activityEnroll);

    @Delete("DELETE FROM tb_activities_enroll WHERE id = #{id}")
    int delete(Integer id);

    @Update("UPDATE tb_activities_enroll SET user_id = #{user_id},activity_id = #{activity_id}," +
            "activity_enroll_state = #{activity_enroll_state} WHERE id = #{id}")
    int update(ActivityEnroll activityEnroll);
}
