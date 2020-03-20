package cn.yangcy.pzc.model.major;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class MajorRepository {

    private static final String TAG = "MajorRepository";
    private MajorDao majorDao;
    public MajorRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        majorDao = dataBase.getMajorDao();
        Log.i(TAG, "数据库连接");
    }

    public LiveData<List<Major>> getMajorListByDepartmentId(int departmentId){
        Log.i(TAG, "通过系别ID 查找 专业: ");
        return majorDao.getMajorListByDepartmentId(departmentId);
    }

    public LiveData<Major> getMajorById(int majorId){
        Log.i(TAG, "通过专业ID 查找 专业: ");
        return majorDao.getMajorById(majorId);
    }

    public LiveData<Major> getMajorIdByName(String majorName){
        Log.i(TAG, "通过专业名 查找 专业: ");
        return majorDao.getMajorByName(majorName);
    }
}
