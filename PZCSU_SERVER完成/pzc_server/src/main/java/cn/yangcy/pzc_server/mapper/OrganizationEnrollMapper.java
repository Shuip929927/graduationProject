package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.OrganizationEnroll;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationEnrollMapper {

    @Select("SELECT * FROM tb_organization_enroll WHERE id = #{id}")
    OrganizationEnroll getOrganizationEnrollById(Integer id);

    @Select("SELECT * FROM tb_organization_enroll WHERE user_id = #{user_id} and organization_id = #{organization_id} ")
    OrganizationEnroll getOrganizationEnrollByUserIdAndOrganizationId(@Param("user_id") Integer userId,@Param("organization_id") Integer organizationId);

    @Select("SELECT * FROM tb_organization_enroll WHERE user_id = #{user_id}")
    List<OrganizationEnroll> getOrganizationEnrollByUserId(Integer userId);

    @Select("SELECT * FROM tb_organization_enroll WHERE organization_id = #{organization_id}")
    List<OrganizationEnroll> getOrganizationEnrollByOrganizationId(Integer organizationId);

    @Select("SELECT * FROM tb_organization_enroll")
    List<OrganizationEnroll> getAllOrganizationEnroll();

    @Insert("INSERT INTO tb_organization_enroll(user_id,organization_id) VALUES" +
            "(#{user_id},#{organization_id})")
    int add(OrganizationEnroll organizationEnroll);

    @Delete("DELETE FROM tb_organization_enroll WHERE id = #{id}")
    int delete(Integer id);

    @Update("UPDATE tb_organization_enroll SET user_id = #{user_id},organization_id = #{organization_id}," +
            "organization_enroll_state = #{organization_enroll_state} WHERE id = #{id}")
    int update(OrganizationEnroll organizationEnroll);


}
