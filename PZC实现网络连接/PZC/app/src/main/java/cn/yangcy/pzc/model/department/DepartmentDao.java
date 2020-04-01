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

    //查询所有部门
    @Query("SELECT * FROM tb_department ORDER BY id ASC")
    LiveData<List<Department>> queryAllDepartment();

    //通过部门名称查询部门
    @Query("SELECT * FROM tb_department WHERE department_name = :departmentName")
    LiveData<Department> queryDepartmentByName(String departmentName);

    //通过部门ID 查询部门
    @Query("SELECT * FROM tb_department WHERE id = :departmentId")
    LiveData<Department> getDepartmentNameById(int departmentId);
}
