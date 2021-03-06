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
        infoPagedList = new LivePagedListBuilder<>(informationDao.queryNewInfoByPaging(),1)
                .build();
    }

    public LiveData<List<Information>> getInfoListLive() {
        return infoListLive;
    }

    public LiveData<PagedList<Information>> getInfoPagedList() {
        return infoPagedList;
    }

    public void addInformationHit(Information information){
        Log.i(TAG, "addInformationHit: "+ information.toString());
        new AddInformationHitsAsynTask(informationDao).execute(information);
    }


    static class AddInformationHitsAsynTask extends AsyncTask<Information,Void,Void>{
        private InformationDao informationDao;

        public AddInformationHitsAsynTask(InformationDao informationDao) {
            this.informationDao = informationDao;
        }

        @Override
        protected Void doInBackground(Information... information) {
            information[0].setHits(information[0].getHits()+1);
            informationDao.updateInformation(information[0]);
            return null;
        }
    }
}
