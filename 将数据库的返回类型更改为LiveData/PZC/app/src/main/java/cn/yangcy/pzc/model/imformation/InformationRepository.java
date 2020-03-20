package cn.yangcy.pzc.model.imformation;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class InformationRepository {

    private final static String TAG = "InformationRepository";
    private InformationDao informationDao;
    private LiveData<List<Information>> infoListLive;
    private LiveData<PagedList<Information>> infoPagedList;

    public InformationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        informationDao = dataBase.getInformationDao();
        infoListLive = informationDao.queryNewInfo();
        Log.i(TAG, "数据库连接 ");
        infoPagedList = new LivePagedListBuilder<>(informationDao.queryNewInfoByPaging(),1)
                .build();
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

    public LiveData<PagedList<Information>> getInfoPagedList() {
        Log.i(TAG, "获取信息列表 Paging");
        return infoPagedList;
    }

    public void addInformationHit(Information information){
        Log.i(TAG, "信息点击数增加 "+ information.toString());
        information.setHits(information.getHits()+1);
        new UpdateAsyncTask(informationDao).execute(information);
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
