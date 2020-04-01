package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Organization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationMapper {

    //查
    @Select("SELECT * FROM tb_organization WHERE id = #{id}")
    Organization getOrganizationById(Integer id);

    //查全部
    @Select("SELECT * FROM tb_organization")
    List<Organization> getAllOrganizationList();

    @Select("SELECT * FROM tb_organization WHERE person_id = #{userId} OR id IN " +
            "(SELECT organization_id FROM tb_organization_enroll WHERE organization_enroll_state = 2 " +
            "AND user_id = #{userId})")
    List<Organization> getUserEnrollOrganizationListByUserId(int userId);

    //增
    @Insert("insert into tb_organization(organization_name,person_in_charge,person_id,number_of_people," +
            "description,isEnroll) " +
            "values(#{organization_name},#{person_in_charge},#{person_id},#{number_of_people}," +
            "#{description},#{isEnroll})")
    int addOrganization(Organization organization);

    //删
    @Delete("delete from tb_organization where id=#{id}")
    int delete(Integer id);

    //改
    @Update("update tb_organization set organization_name=#{organization_name},person_in_charge=#{person_in_charge}," +
            "person_id=#{person_id},number_of_people=#{number_of_people},description=#{description}" +
            ",isEnroll=#{isEnroll} where id=#{id}")
    int update(Organization organization);

}
