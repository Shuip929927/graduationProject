package cn.yangcy.pzc.model.department;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DepartmentDao {

    @Insert
    void insertDepartment(Department... departments);

//    @Insert
//    void insertDepartmentString(String... strings);

    @Delete
    void deleteDepartment(Department department);

    @Update
    void updateDepartment(Department department);

    @Query("SELECT department_name FROM tb_department ORDER BY id ASC")
//        使用LiveData 是异步查询，
//        查询结果还没返回时就getValues
//        所以departmentList 为NULL
//    LiveData<List<String>> queryAllDepartment();
    List<String> queryAllDepartment();

}
