package cn.yangcy.pzc.model.organization;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.model.department.Department;

public class OrganizationRepository {

    private static final String TAG = "OrganizationRepository";
    private OrganizationDao organizationDao;

    public OrganizationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationDao = dataBase.getOrganizationDao();
        Log.i(TAG, "数据库连接");
    }
    public void addOrganization(Organization organization){
        Log.i(TAG, "增加学生组织 ");
        new AddAsync(organizationDao).execute(organization);
    }

    public void deleteOrganization(Organization organization){
        Log.i(TAG, "删除学生组织 ");
        new DeleteAsync(organizationDao).execute(organization);
    }

    public void updateOrganization(Organization organization){
        Log.i(TAG, "更新学生组织信息 ");
        new UpdateAsync(organizationDao).execute(organization);
    }

    public LiveData<List<Organization>> getAllOrganization(){
        Log.i(TAG, "查询所有学生组织 ");
        return organizationDao.queryAllOrganization();
    }
    public LiveData<Organization> queryOrganizationById(int organizationId){
        Log.i(TAG, "通过ID查询学生组织 ");
        return organizationDao.queryOrganizationById(organizationId);
    }

    public LiveData<Organization> queryOrganizationIdByName(String organizationName){
        Log.i(TAG, "通过学生组织名查询学生组织: ");
        return organizationDao.queryOrganizationIdByName(organizationName);
    }

    public LiveData<Organization> queryPersonInChargeOrgId(int personInChargeId){
        Log.i(TAG, "通过学生组织负责人ID查询学生组织: ");
        return organizationDao.queryPersonInChargeOrgId(personInChargeId);
    }

    public LiveData<List<Organization>> queryUserEnrollOrganization(List<Integer> list){
        Log.i(TAG, "通过学生组织列表查询一些学生组织");
        int[] organizations = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "将用户List转为String[]: "+ list.get(i));
            organizations[i] = list.get(i);
        }
        return organizationDao.queryUserEnrollOrganization(organizations);
    }

    static class AddAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        AddAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: AddAsync");
            organizationDao.insertOrganization(organizations[0]);
            return null;
        }
    }
    static class DeleteAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        DeleteAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            organizationDao.deleteOrganization(organizations[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        UpdateAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            organizationDao.updateOrganization(organizations[0]);
            return null;
        }
    }
}
