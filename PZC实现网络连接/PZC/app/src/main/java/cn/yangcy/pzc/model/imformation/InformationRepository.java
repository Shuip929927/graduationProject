package cn.yangcy.pzc.model.imformation;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

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
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationRepository {

    private final static String TAG = "InformationRepository";
    private InformationDao informationDao;
    private LiveData<List<Information>> infoListLive;
    private LiveData<PagedList<Information>> infoPagedList;
    private Gson gson = new Gson();


    public InformationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        informationDao = dataBase.getInformationDao();
        infoListLive = informationDao.queryNewInfo();
        Log.i(TAG, "数据库连接 ");
//        infoPagedList = new LivePagedListBuilder<>(informationDao.queryNewInfoByPaging(),1)
//                .build();
    }

    public void addInformation(Information information){
        Log.i(TAG, "增加信息 ");
        new AddAsync(informationDao).execute(information);
    }

    public void deleteInformation(Information information){
        Log.i(TAG, "删除信息 ");
        new DeleteAsync(informationDao).execute(information);
    }

    public LiveData<List<Information>> getInfoListLive() {
        Log.i(TAG, "获取信息列表 LiveData");
        return infoListLive;
    }

    public LiveData<List<Information>> queryInfoListByNet(){
        Log.i(TAG, "通过网络从服务器上获取信息列表 LiveData ");
        final MutableLiveData<List<Information>> queryAllInfoByNet = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GEI_ALL_INFORMATION, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                queryAllInfoByNet.postValue(null);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String res = response.body().string();
                    Log.i(TAG, "onResponse: " + res);
                    Type type = new TypeToken<List<Information>>(){}.getType();
                    List<Information> infoResult = gson.fromJson(res,type);
                    queryAllInfoByNet.postValue(infoResult);
                }
                response.close();
            }
        });
        return queryAllInfoByNet;
    }

//    public LiveData<PagedList<Information>> getInfoPagedList() {
//        Log.i(TAG, "获取信息列表 Paging");
//        return infoPagedList;
//    }

    public void addInformationHit(Information information){
        Log.i(TAG, "信息点击数增加 "+ information.toString());
        information.setHits(information.getHits()+1);
        new UpdateAsyncTask(informationDao).execute(information);
    }

    public void addInformationHintsByNet(Information information){
        information.setHits(information.getHits()+1);

        String json = gson.toJson(information);
        Log.i(TAG, "信息点击数增加 NET "+ json);
        final RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_INFORMATION, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    static class AddAsync extends AsyncTask<Information,Void,Void> {
        private InformationDao informationDao;

        AddAsync(InformationDao informationDao) {
            this.informationDao = informationDao;
        }
        @Override
        protected Void doInBackground(Information... informations) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            informationDao.insertInformation(informations[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<Information,Void,Void> {
        private InformationDao informationDao;

        DeleteAsync(InformationDao informationDao) {
            this.informationDao = informationDao;
        }
        @Override
        protected Void doInBackground(Information... informations) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            informationDao.deleteInformation(informations[0]);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Information,Void,Void>{
        private InformationDao informationDao;

        UpdateAsyncTask(InformationDao informationDao) {
            this.informationDao = informationDao;
        }

        @Override
        protected Void doInBackground(Information... information) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            informationDao.updateInformation(information[0]);
            return null;
        }
    }

}
