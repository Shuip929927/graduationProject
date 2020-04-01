package cn.yangcy.pzc.model.department;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.net.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DepartmentRepository {

    private static final String TAG = "DepartmentRepository";
    private DepartmentDao departmentDao;
    private Gson gson = new Gson();


    public DepartmentRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        departmentDao = dataBase.getDepartmentDao();
        Log.i(TAG, "数据库连接 ");
        Log.i(TAG, "查询的所有部门 ");
    }

    public LiveData<List<Department>> getDepartmentList() {
        Log.i(TAG, "获取所有部门 ");
        return departmentDao.queryAllDepartment();
    }

    public LiveData<List<Department>> getDepartmentListNet() {
        Log.i(TAG, "通过网络查询获取所有部门 ");
        final MutableLiveData<List<Department>> depList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_DEPARTMENT_ALL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Type type = new TypeToken<List<Department>>() {}.getType();
                List<Department> infoResult = gson.fromJson(res,type);
                depList.postValue(infoResult);
                response.close();
            }
        });
        return depList;
    }


    public LiveData<Department> getDepartmentLiveById(int departmentId) {
        Log.i(TAG, "通过部门ID 查询部门 ");
        return departmentDao.getDepartmentNameById(departmentId);
    }

    public LiveData<Department> getDepartmentLiveByIdNet(int departmentId) {
        Log.i(TAG, "通过部门ID 查询部门 ");
        final MutableLiveData<Department> dep = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_DEPARTMENT_BY_ID + departmentId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Department resDep = gson.fromJson(res, Department.class);
                dep.postValue(resDep);
                response.close();
            }
        });
        return dep;
    }

}
