package cn.yangcy.pzc.model.department;

import android.content.Context;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class DepartmentRepository {

    private DepartmentDao departmentDao;
    //    private LiveData<List<String>> departmentListLive;
    private List<String> departmentList;

    public DepartmentRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        departmentDao = dataBase.getDepartmentDao();

//        使用LiveData 是异步查询，
//        查询结果还没返回时就getValues
//        所以departmentList 为NULL
//        departmentListLive = departmentDao.queryAllDepartment();
//        departmentList = departmentListLive.getValue();
        departmentList = departmentDao.queryAllDepartment();
    }

    public List<String> getDepartmentList() {
        return departmentList;
    }

    public Department queryDepartmentByName(String departmentName){
        return departmentDao.queryDepartmentByName(departmentName);
    }

}
