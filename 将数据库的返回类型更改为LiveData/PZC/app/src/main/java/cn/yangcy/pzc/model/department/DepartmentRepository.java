package cn.yangcy.pzc.model.department;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class DepartmentRepository {

    private static final String TAG = "DepartmentRepository";
    private DepartmentDao departmentDao;

    private LiveData<List<Department>> departmentList;

    public DepartmentRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        departmentDao = dataBase.getDepartmentDao();
        Log.i(TAG, "数据库连接 ");
        Log.i(TAG, "查询的所有部门 ");
        departmentList = departmentDao.queryAllDepartment();
    }

    public LiveData<List<Department>> getDepartmentList() {
        Log.i(TAG, "获取查询的所有部门 ");
        return departmentList;
    }

    public LiveData<Department> getDepartmentByName(String departmentName) {
        Log.i(TAG, "通过部门名称查询部门 ");
        return departmentDao.queryDepartmentByName(departmentName);
    }

    public LiveData<Department> getDepartmentLiveById(int departmentId) {
        Log.i(TAG, "通过部门ID 查询部门 ");
        return departmentDao.getDepartmentNameById(departmentId);
    }

}
