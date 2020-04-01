package cn.yangcy.pzc.model.major;

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

public class MajorRepository {

    private static final String TAG = "MajorRepository";
    private MajorDao majorDao;
    private Gson gson = new Gson();

    public MajorRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        majorDao = dataBase.getMajorDao();
        Log.i(TAG, "数据库连接");
    }

    public LiveData<List<Major>> getMajorListByDepartmentId(int departmentId){
        Log.i(TAG, "通过系别ID 查找 专业: ");
        return majorDao.getMajorListByDepartmentId(departmentId);
    }

    public LiveData<List<Major>> getMajorListByDepartmentIdNet(int departmentId){
        Log.i(TAG, "通过系别ID 查找 专业NEt: ");
        final MutableLiveData<List<Major>> majorList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_MAJOR_BY_DEP_ID + departmentId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<Major>>(){}.getType();
                    List<Major> resList = gson.fromJson(res,type);
                    majorList.postValue(resList);
                }
                response.close();
            }
        });
        return majorList;
    }

    public LiveData<Major> getMajorById(int majorId){
        Log.i(TAG, "通过专业ID 查找 专业: ");
        return majorDao.getMajorById(majorId);
    }

    public LiveData<Major> getMajorByIdNet(int majorId){
        Log.i(TAG, "通过专业ID 查找 专业 NET: ");
        final MutableLiveData<Major> major = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL+Config.GET_MAJOR_BY_ID + majorId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Major resMajor = gson.fromJson(res,Major.class);
                    major.postValue(resMajor);
                }
                response.close();
            }
        });
        return major;
    }

}
