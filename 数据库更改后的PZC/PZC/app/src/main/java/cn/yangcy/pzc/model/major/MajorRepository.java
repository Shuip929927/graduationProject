package cn.yangcy.pzc.model.major;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class MajorRepository {

    private static final String TAG = "MajorRepository";
    private MajorDao majorDao;
    public MajorRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        majorDao = dataBase.getMajorDao();
        Log.i(TAG, "MajorRepository: DB connect");
    }

    public List<String> getMajorListByDepartmentId(int departmentId){
        return majorDao.getMajorListByDepartmentId(departmentId);
    }

    public String getMajorNameById(int majorId){
        return majorDao.getMajorNameById(majorId);
    }

    public int getMajorIdByName(String majorName){
        return majorDao.getMajorIdByName(majorName);
    }
}
