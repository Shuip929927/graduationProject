package cn.yangcy.pzc.model.enroll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class OrganizationEnrollRepository {
    private static final String TAG = "OERepository";
    private OrganizationEnrollDao organizationEnrollDao;

    public OrganizationEnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationEnrollDao = dataBase.getOrganizationEnrollDao();
        Log.i(TAG, "数据库连接 ");
    }

    public void addOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "添加学生组织报名信息 ");
        new AddAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public void deleteOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "删除学生组织报名信息 ");
        new DeleteAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public void updateOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "更新学生组织报名信息 ");
        new UpdateAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public LiveData<OrganizationEnroll> queryOrganizationEnrollLive(int userAccount, int organizationId){
        Log.i(TAG, "查询一个用户报名的一个组织的信息 ");
        return organizationEnrollDao.queryOrganizationEnrollLive(userAccount,organizationId);
    }

    public LiveData<List<OrganizationEnroll>> queryOrganizationMember(int organizationId){
        Log.i(TAG, "查询一个组织的所有报名状态为2的报名信息 ");
        return organizationEnrollDao.queryOrganizationMember(organizationId);
    }

    public LiveData<List<OrganizationEnroll>> queryOrganizationEnrollMember(int organizationId){
        Log.i(TAG, "查询一个组织的所有报名状态为1的报名信息 ");
        return organizationEnrollDao.queryOrganizationEnrollMember(organizationId);
    }

    public LiveData<List<OrganizationEnroll>> queryUserEnrollOrganizationList(int userId){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID ");
        return organizationEnrollDao.queryUserEnrollOrganizationList(userId);
    }
    static class AddAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        AddAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }

        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: AddAsync");
            organizationEnrollDao.insertOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        public DeleteAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }
        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            organizationEnrollDao.deleteOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        public UpdateAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }
        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            organizationEnrollDao.updateOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }
}
