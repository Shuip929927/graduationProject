package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.fragment.stundentunoin.StudentUnionListPageActivities;
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

public class StudentUnionViewModel extends AndroidViewModel {

    private static final String TAG = "StudentUnionViewModel";
    private SharedPreferences sharedPreferences;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;
    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;
    private OrganizationEnrollRepository oERepository;
    private ActivitiesEnrollRepository aERepository;

    private int activitiesId;
    private LiveData<List<Activities>> activitiesLiveList;
    private LiveData<ActivitiesEnroll> activitiesEnrollLive;

    private int organizationId;
    private LiveData<List<Organization>> organizationLiveList;
    private LiveData<OrganizationEnroll> organizationEnrollLive;

    private List<Integer> memberList;
    private List<Integer> memberEnrollList;

    private MutableLiveData<List<Activities>> activitiesMLiveByNet;

    private LiveData<List<Activities>> activitiesLiveByNet;

    private String personInChargeName;

    public StudentUnionViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);

        userRepository = new UserRepository(getApplication());
        departmentRepository = new DepartmentRepository(getApplication());
        majorRepository = new MajorRepository(getApplication());
        activitiesRepository = new ActivitiesRepository(getApplication());
        organizationRepository = new OrganizationRepository(getApplication());
        aERepository = new ActivitiesEnrollRepository(getApplication());
        oERepository = new OrganizationEnrollRepository(getApplication());
        activitiesLiveList = activitiesRepository.getAllActivities();
        Log.i(TAG, " 创建 Repository 实例 ");
    }

    public int getSpUserAccount(){
        return userRepository.getSpUserAccount();
    }

    public int getSpUserPower(){
        return userRepository.getSpUserPower();
    }

    public int getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(int activitiesId) {
        this.activitiesId = activitiesId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    //User
    public LiveData<User> getUserLiveByAccount(int userAccount){
        Log.i(TAG, "获取用户信息");
        return userRepository.queryUserByAccount(userAccount);
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

    //ActivitiesEnroll
    public LiveData<ActivitiesEnroll> getActivitiesEnrollByUserAccountAndActivitiesId(int userAccount,int activitiesId){
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
    //OrganizationEnroll
    public LiveData<OrganizationEnroll> getOrganizationEnrollLive(int userAccount,int organizationId){
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

}

