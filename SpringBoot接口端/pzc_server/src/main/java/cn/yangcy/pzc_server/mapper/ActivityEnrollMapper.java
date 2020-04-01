package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.ActivityEnroll;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityEnrollMapper {

    @Select("SELECT * FROM tb_activities_enroll WHERE id = #{id} GROUP BY activity_id ORDER BY activity_enroll_state DESC")
    ActivityEnroll getActivityEnrollById(Integer id);

    @Select("SELECT * FROM tb_activities_enroll WHERE user_id = #{user_id} and activity_id = #{activity_id} ")
    ActivityEnroll getActivityEnrollByUserIdAndActivityId(@Param("user_id") Integer user_id, @Param("activity_id") Integer activity_id);

    @Select("SELECT * FROM tb_activities_enroll WHERE user_id = #{user_id} AND activity_enroll_state = 2")
    List<ActivityEnroll> getActivityEnrollByUserId(Integer user_id);

    @Select("SELECT * FROM tb_activities_enroll WHERE activity_id = #{activity_id} AND activity_enroll_state = 2")
    List<ActivityEnroll> getActivityEnrollState2ByActivityId(Integer activity_id);

    @Select("SELECT * FROM tb_activities_enroll WHERE activity_id = #{activity_id} AND activity_enroll_state = 1")
    List<ActivityEnroll> getActivityEnrollState1ByActivityId(Integer activity_id);

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
