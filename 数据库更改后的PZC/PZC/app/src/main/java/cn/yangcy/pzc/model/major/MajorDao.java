package cn.yangcy.pzc.model.major;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MajorDao {

    @Query("SELECT major_name FROM tb_major WHERE department_id = :departmentId")
    List<String> getMajorListByDepartmentId(int departmentId);

    @Query("SELECT major_name FROM tb_major WHERE id = :majorId")
    String getMajorNameById(int majorId);

    @Query("SELECT id FROM tb_major WHERE major_name = :majorName")
    int getMajorIdByName(String majorName);
}
