package cn.yangcy.pzc.model.enroll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class ActivitiesEnrollRepository {

    private static final String TAG = "AERepository";
    private ActivitiesEnrollDao activitiesEnrollDao;

    public ActivitiesEnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesEnrollDao = dataBase.getActivitiesEnrollDao();
        Log.i(TAG, "数据库连接 ");
    }

    //增
    public void addActivitiesEnroll(ActivitiesEnroll activitiesEnroll){
        Log.i(TAG, "添加学生活动报名信息 ");
        new AddAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    //删
    public void deleteActivitiesEnroll(ActivitiesEnroll activitiesEnroll){
        Log.i(TAG, "修改学生活动报名信息 ");
        new DeleteAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    //改
    public void updateActivitiesEnroll(ActivitiesEnroll activitiesEnroll){
        Log.i(TAG, "修改学生活动报名信息 ");
        new UpdateAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    public LiveData<ActivitiesEnroll> getActivitiesEnrollLive(int userAccount, int activitiesId){
        Log.i(TAG, "通过UserAccount和活动Id获取活动报名信息 ");
        return activitiesEnrollDao.queryActivitiesEnrollLive(userAccount,activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> queryActivitiesMember(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为2的用户 ");
        return activitiesEnrollDao.queryActivitiesMember(activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> queryActivitiesEnrollMember(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为1的用户 ");
        return activitiesEnrollDao.queryActivitiesEnrollMember(activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> queryUserEnrollActivities(int userId){
        Log.i(TAG, "通过用户Account查询 用户报名成功（状态为2）的活动 ");
        return activitiesEnrollDao.queryUserEnrollActivities(userId);
    }

    static class AddAsync extends AsyncTask<ActivitiesEnroll,Void,Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        AddAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: AddAsync");
            activitiesEnrollDao.insertEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<ActivitiesEnroll,Void,Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        DeleteAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            activitiesEnrollDao.deleteEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<ActivitiesEnroll,Void,Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        UpdateAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            activitiesEnrollDao.updateEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }
}
