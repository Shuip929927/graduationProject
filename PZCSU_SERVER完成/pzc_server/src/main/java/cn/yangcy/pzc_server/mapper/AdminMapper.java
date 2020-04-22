package cn.yangcy.pzc_server.mapper;

import cn.yangcy.pzc_server.bean.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Insert("INSERT INTO tb_admin VALUES(#{account},#{password},#{name},#{phoneNumber})")
    int addAdmin(Admin admin);

    @Delete("DELETE FROM tb_admin WHERE account=#{account}")
    int deleteAdmin(String account);

    @Update("UPDATE tb_admin SET password=#{password},name=#{name} WHERE account=#{account}")
    int updateAdmin(Admin admin);

    @Select("SELECT * FROM tb_admin WHERE account = #{account}")
    Admin getAdminByAccount(String account);

    @Select("SELECT * FROM tb_admin")
    List<Admin> getAllAdmin();
}
