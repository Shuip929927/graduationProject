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
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.enroll.ActivitiesEnrollRepository;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.enroll.OrganizationEnrollRepository;
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
    private ActivitiesEnrollRepository aERepository;
    private OrganizationEnrollRepository oERepository;

    private List<Integer> myEnrollOrganizationIdList;
    private LiveData<List<Organization>> myEnrollOrganizationLiveData;

    private List<Integer> myEnrollActivitiesIdList;
    private LiveData<List<Activities>> myEnrollActivitiesLiveData;

    private int myChargeOrganizationId;
    private LiveData<List<Activities>> myHoldActivitiesLiveData;

    public MyPageViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        majorRepository = new MajorRepository(application);
        organizationRepository = new OrganizationRepository(application);
        activitiesRepository = new ActivitiesRepository(application);
        aERepository = new ActivitiesEnrollRepository(application);
        oERepository = new OrganizationEnrollRepository(application);
    }

    //User
    public LiveData<User> getUserLiveByAccount(int userAccount){
        Log.i(TAG, "获取用户信息");
        return userRepository.queryUserByAccount(userAccount);
    }

    public void updateUser(User user){
        Log.i(TAG, "更新用户信息");
        userRepository.updateUser(user);
    }
    //Activities
    public LiveData<List<Activities>> getAllActivitiesListLive(){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getAllActivities();
    }

    public LiveData<Activities> getActivitiesLiveById(int activitiesId){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getActivitiesById(activitiesId);
    }

    public void updateActivities(Activities activities){
        Log.i(TAG, "更新活动信息");
        activitiesRepository.updateActivities(activities);
    }


    public LiveData<List<User>> queryUserLiveData(List<Integer> list){
        Log.i(TAG, "通过用户列表查询一些用户");
        return userRepository.queryUserLiveData(list);
    }

    public LiveData<List<Activities>> getOrganizationHoldActivitiesList(int myChargeOrganizationId){
        Log.i(TAG, "通过学生组织ID查询该组织举办的所有活动");
        return activitiesRepository.getOrganizationHoldActivitiesList(myChargeOrganizationId);
    }
    public LiveData<List<Activities>> getUserEnrollActivitiesList(List<Integer> list) {
        Log.i(TAG, "通过活动id列表查询该所有活动");
        return activitiesRepository.getUserEnrollActivitiesList(list);
    }

    //ActivitiesEnroll
    public LiveData<ActivitiesEnroll> getActivitiesEnrollByUserAccountAndActivitiesId(int userAccount, int activitiesId){
        Log.i(TAG, "通过UserId 以及 参数中的ActivitiesID 查找一个 活动报名记录 ");
        LiveData<ActivitiesEnroll> aeLive = aERepository.getActivitiesEnrollLive(userAccount,activitiesId);
        if(aeLive == null){
            Log.i(TAG, "新增！！ ");
            addAE(new ActivitiesEnroll(activitiesId,activitiesId));
        }
        return aERepository.getActivitiesEnrollLive(userAccount,activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> getActivitiesMemberListLive(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为2（报名成功）的用户(Member) ");
        return aERepository.queryActivitiesMember(activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> getActivitiesEnrollMemberListLive(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为1（报名待审核）的用户(Enroll) ");
        return aERepository.queryActivitiesEnrollMember(activitiesId);
    }

    public LiveData<List<ActivitiesEnroll>> queryUserEnrollActivities(int userId){
        Log.i(TAG, "通过用户Account查询 用户报名成功（状态为2）的活动 ");
        return aERepository.queryUserEnrollActivities(userId);
    }

    public void addAE(ActivitiesEnroll ae){
        aERepository.addActivitiesEnroll(ae);
    }

    public void updateAE(ActivitiesEnroll ae){
        Log.i(TAG, "更新报名记录");
        aERepository.updateActivitiesEnroll(ae);
    }

    public void deleteAE(ActivitiesEnroll ae){
        Log.i(TAG, "删除报名记录");
        aERepository.deleteActivitiesEnroll(ae);
    }


    //Organization
    public LiveData<List<Organization>> getAllOrganizationListLive(){
        Log.i(TAG, "获取所有学生组织 ");
        return organizationRepository.getAllOrganization();
    }

    public LiveData<Organization> getOrganizationLiveById(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 ");
        return organizationRepository.queryOrganizationById(organizationId);
    }

    public LiveData<List<Organization>> queryUserEnrollOrganization(List<Integer> list){
        Log.i(TAG, "通过学生组织IdList查询一些学生组织");
        return organizationRepository.queryUserEnrollOrganization(list);
    }
    //OrganizationEnroll
    public LiveData<OrganizationEnroll> getOrganizationEnrollLive(int userAccount, int organizationId){
        Log.i(TAG, "通过UserId 以及 OrganizationId 查找一个学生组织报名记录 ");
        return oERepository.queryOrganizationEnrollLive(userAccount,organizationId);
    }

    public LiveData<List<OrganizationEnroll>> getOrganizationMemberListLive(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为2 的报名信息 ");
        return oERepository.queryOrganizationMember(organizationId);
    }

    public LiveData<List<OrganizationEnroll>> getOrganizationEnrollMemberListLive(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为1 的报名信息 ");
        return oERepository.queryOrganizationEnrollMember(organizationId);

    }
    public LiveData<List<OrganizationEnroll>> queryUserEnrollOrganizationList(int userId){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID ");
        return oERepository.queryUserEnrollOrganizationList(userId);
    }

    public void addOE(OrganizationEnroll oe){
        Log.i(TAG, "新增学生组织报名记录");
        oERepository.addOE(oe);
    }

    public void updateOE(OrganizationEnroll oe){
        Log.i(TAG, "更新学生组织报名记录");
        oERepository.updateOE(oe);
    }

    public void deleteOE(OrganizationEnroll oe){
        Log.i(TAG, "删除学生组织报名记录");
        oERepository.deleteOE(oe);
    }

    //Department

    public LiveData<List<Department>> getDepartmentList() {
        Log.i(TAG, "获取Department LiveData<List<Department>>列表 ");
        return departmentRepository.getDepartmentList();
    }

    public LiveData<Department> getDepartmentLiveById(int departmentId){
        Log.i(TAG, "通过departmentId 获取部门信息 ");
        return departmentRepository.getDepartmentLiveById(departmentId);
    }

    public void updateOrganization(Organization organization){
        Log.i(TAG, "更新学生组织信息 ");
        organizationRepository.updateOrganization(organization);
    }

    //Major
    public LiveData<Major> getMajorLiveById(int majorId){
        Log.i(TAG, "majorId 获取专业信息 ");
        return majorRepository.getMajorById(majorId);
    }
    public LiveData<List<Major>> getMajorList(int departmentId) {
        Log.i(TAG, "通过DepartmentId 获取Major List ");
        return majorRepository.getMajorListByDepartmentId(departmentId);
    }
    
}
