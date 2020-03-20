package cn.yangcy.pzc.model.major;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MajorDao {

    //通过系ID 查询 专业
    @Query("SELECT * FROM tb_major WHERE department_id = :departmentId")
    LiveData<List<Major>> getMajorListByDepartmentId(int departmentId);

    //通过专业ID 查询专业
    @Query("SELECT * FROM tb_major WHERE id = :majorId")
    LiveData<Major> getMajorById(int majorId);

    //通过专业名 查询专业
    @Query("SELECT * FROM tb_major WHERE major_name = :majorName")
    LiveData<Major> getMajorByName(String majorName);
}
