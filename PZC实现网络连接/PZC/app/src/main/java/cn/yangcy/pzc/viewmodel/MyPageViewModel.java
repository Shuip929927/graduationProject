package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class MyPageViewModel extends AndroidViewModel {

    private static final String TAG = "MyPageViewModel";
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;
    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;

    public MyPageViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        majorRepository = new MajorRepository(application);
        organizationRepository = new OrganizationRepository(application);
        activitiesRepository = new ActivitiesRepository(application);
    }

    //User
    public LiveData<User> getUserLiveByAccount(int userAccount){
        Log.i(TAG, "获取用户信息");
        return userRepository.queryUserByAccount(userAccount);
    }

    public LiveData<User> getUserLiveByAccountNet(int userAccount){
        Log.i(TAG, "获取用户信息(Net)");
        return userRepository.queryUserByAccountNet(userAccount);
    }

    public void updateUser(User user){
        Log.i(TAG, "更新用户信息");
        userRepository.updateUser(user);
    }

    public void updateUserNet(User user){
        Log.i(TAG, "更新用户信息 Net");
        userRepository.updateUserByNet(user);
        updateUser(user);
    }

    //Activities
    public LiveData<List<Activities>> getOrganizationHoldActivitiesList(int userId){
        Log.i(TAG, "通过UserID查询该其负责组织举办的所有活动");
        return activitiesRepository.getOrganizationHoldActivitiesList(userId);
    }

    public LiveData<List<Activities>> getOrganizationHoldActivitiesListNet(int userId){
        Log.i(TAG, "通过UserID查询该其负责组织举办的所有活动 Net");
        return activitiesRepository.getOrganizationHoldActivitiesListNet(userId);
    }

    public LiveData<List<Activities>> getUserEnrollActivitiesList(int userId) {
        Log.i(TAG, "通过UserId列表查询该用户参加的所有活动");
        return activitiesRepository.getUserEnrollActivitiesList(userId);
    }

    public LiveData<List<Activities>> getUserEnrollActivitiesListNet(int userId) {
        Log.i(TAG, "通过UserId列表查询该用户参加的所有活动 Net");
        return activitiesRepository.getUserEnrollActivitiesListNet(userId);
    }

    //Organization
    public LiveData<Organization> getOrganizationLiveById(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 ");
        return organizationRepository.queryOrganizationById(organizationId);
    }

    public LiveData<Organization> getOrganizationLiveByIdNet(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 Net");
        return organizationRepository.queryOrganizationByIdNet(organizationId);
    }

    public LiveData<List<Organization>> queryUserEnrollOrganizationList(int userId){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID ");
        return organizationRepository.queryUserEnrollOrganizationList(userId);
    }

    public LiveData<List<Organization>> queryUserEnrollOrganizationListNet(int userId){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID Net");
        return organizationRepository.queryUserEnrollOrganizationListNet(userId);
    }

    //Department
    public LiveData<List<Department>> getDepartmentList() {
        Log.i(TAG, "获取Department LiveData<List<Department>>列表 ");
        return departmentRepository.getDepartmentList();
    }

    public LiveData<List<Department>> getDepartmentListNet() {
        Log.i(TAG, "获取Department LiveData<List<Department>>列表 Net");
        return departmentRepository.getDepartmentListNet();
    }

    //Major
    public LiveData<List<Major>> getMajorList(int departmentId) {
        Log.i(TAG, "通过DepartmentId 获取Major List ");
        return majorRepository.getMajorListByDepartmentId(departmentId);
    }

    public LiveData<List<Major>> getMajorListNet(int departmentId) {
        Log.i(TAG, "通过DepartmentId 获取Major List Net ");
        return majorRepository.getMajorListByDepartmentIdNet(departmentId);
    }
    
}
