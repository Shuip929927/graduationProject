package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BackGroundMapper {

    //后台增加学生组织报名信息
    @Insert("INSERT INTO tb_organization_enroll(user_id,organization_id,organization_enroll_state) VALUES" +
            "(#{user_id},#{organization_id},#{organization_enroll_state})")
    int addOeBG(OrganizationEnroll organizationEnroll);

    //后台查询学生组织报名信息
    @Select("SELECT tb_user.*,id,organization_id,organization_enroll_state FROM tb_user "
            + "INNER JOIN tb_organization_enroll ON tb_organization_enroll.user_id = tb_user.account "
            + "WHERE organization_enroll_state like #{organization_enroll_state} AND tb_organization_enroll.user_id like #{user_id} AND tb_organization_enroll.organization_id like #{organization_id} "
            + "AND organization_enroll_state !=0 "
            + "ORDER BY organization_id ASC")
    List<BackGroundOrgMember> getOrgMemberListBG(String organization_enroll_state, String user_id, String organization_id);

    //后台查所有学生活动
    @Select("SELECT * FROM tb_activities WHERE organization_id like #{organization_id} AND activity_state like #{activity_state} " +
            "AND isDelete = 0 ORDER BY create_time DESC")
    List<Activity> getAllActivityBG(String organization_id, String activity_state);

    //后台增加活动报名信息
    @Insert("INSERT INTO tb_activities_enroll(user_id,activity_id,activity_enroll_state) VALUES (#{user_id},#{activity_id},#{activity_enroll_state})")
    int addAeBG(ActivityEnroll activityEnroll);

    //后台查询活动报名信息
    @Select("SELECT tb_user.*,id,activity_id,activity_enroll_state FROM tb_user "
            + "INNER JOIN tb_activities_enroll ON tb_activities_enroll.user_id = tb_user.account "
            + "WHERE activity_enroll_state like #{activity_enroll_state} AND tb_activities_enroll.user_id like #{user_id} AND tb_activities_enroll.activity_id like #{activity_id} "
            + "AND activity_enroll_state !=0 "
            + "ORDER BY activity_id DESC")
    List<BackGroundActMember> getActMemberListBG(String activity_enroll_state, String user_id, String activity_id);

    //后台查全部用户
    @Select("SELECT * FROM tb_user WHERE account like #{account} AND gender like #{gender} AND departmentId like #{departmentId}")
    List<User> getAllUserBG(String account, String gender, String departmentId);


    //后台查全部通知信息
    @Select("SELECT * FROM tb_information WHERE id like #{id} AND type like #{type} AND isDelete like #{isDelete} ORDER BY createOn DESC")
    List<Information> getAllInformationListBG(String id, String type, String isDelete);

    //后台查询专业
    @Select("SELECT * FROM tb_major WHERE department_id like #{department_id}")
    List<Major> getMajorListBG(String department_id);

}
