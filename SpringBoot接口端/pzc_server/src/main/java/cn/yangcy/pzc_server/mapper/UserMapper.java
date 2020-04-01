package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

// @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，
// 所以统一配置@MapperScan在扫描路径在application类中
@Mapper
public interface UserMapper {
    //查
    @Select("SELECT * FROM tb_user WHERE account = #{account}")
    User getUserByAccount(Integer account);

    //根据accounts列表查询
//    @Select({
//            "<script>",
//            "SELECT * FROM tb_user where account in",
//            "<foreach collection='accounts' item='item' open='(' separator=',' close=')'>",
//            "#{item}",
//            "</foreach>",
//            "</script>"
//    })
//    List<User> getUserListByAccounts(@Param("accounts") List<Integer> accounts);

    @Select("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_activities_enroll where activity_enroll_state = 2 " +
            "AND activity_id = #{activityId})")
    List<User> getActivityEnrollState2UserList(Integer activityId);

    @Select("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_activities_enroll where activity_enroll_state = 1 " +
            "AND activity_id = #{activityId})")
    List<User> getActivityEnrollState1UserList(Integer activityId);

    @Select("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_organization_enroll where organization_enroll_state = 2 " +
            "AND organization_id = #{organizationId})")
    List<User> getOrganizationEnrollState2UserList(Integer organizationId);

    @Select("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_organization_enroll where organization_enroll_state = 1 " +
            "AND organization_id = #{organizationId})")
    List<User> getOrganizationEnrollState1UserList(Integer organizationId);

    //查全部
    @Select("SELECT * FROM tb_user")
    public List<User> getAllUserList();

    //增 insert into DB_PZC.tb_user values('1234','81dc9bdb52d04dc20036dbd8313ed055','1','test','81dc9bdb52d04dc20036dbd8313ed055','计算机工程系','软件工程','1','6');
    @Insert("insert into tb_user values(#{account},#{password},#{gender},#{name},#{phoneNumber}," +
            "#{departmentId},#{majorId},#{classes},#{power})" )
    public int addUser(User user);

    //删
    @Delete("delete from tb_user where account=#{account}")
    public int delete(Integer account);

//    //改
//    @Update("update tb_user set password=#{user.password},gender=#{user.gender}," +
//            "name=#{user.name},phoneNumber=#{user.phoneNumber},departmentId=#{user.departmentId}" +
//            ",majorId=#{user.majorId},classes=#{user.classes},power=#{user.power} where" +
//            " account=#{user.account}")
//    public int update(@Param("user") User user);
    //改
    @Update("update tb_user set password=#{password},gender=#{gender}," +
            "name=#{name},phoneNumber=#{phoneNumber},departmentId=#{departmentId}" +
            ",majorId=#{majorId},classes=#{classes},power=#{power} where" +
            " account=#{account}")
    public int update(User user);


}
