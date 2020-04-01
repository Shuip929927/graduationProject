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

public class StudentUnionViewModel extends AndroidViewModel {

    private static final String TAG = "StudentUnionViewModel";
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;
    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;
    private OrganizationEnrollRepository oERepository;
    private ActivitiesEnrollRepository aERepository;

    private int activitiesId;

    private int organizationId;


    public StudentUnionViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(getApplication());
        departmentRepository = new DepartmentRepository(getApplication());
        majorRepository = new MajorRepository(getApplication());
        activitiesRepository = new ActivitiesRepository(getApplication());
        organizationRepository = new OrganizationRepository(getApplication());
        aERepository = new ActivitiesEnrollRepository(getApplication());
        oERepository = new OrganizationEnrollRepository(getApplication());
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

    public LiveData<User> getUserLiveByAccountNet(int userAccount){
        Log.i(TAG, "获取用户信息 Net");
        return userRepository.queryUserByAccountNet(userAccount);
    }

    public void updateUser(User user){
        Log.i(TAG, "更新用户信息 ");
        userRepository.updateUser(user);
    }

    public void updateUserNet(User user){
        Log.i(TAG, "更新用户信息 Net");
        userRepository.updateUserByNet(user);
        updateUser(user);
    }
    //Activities
    public LiveData<List<Activities>> getAllActivitiesListLive(){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getAllActivities();
    }

    public LiveData<List<Activities>> getAllActivitiesListLiveNet(){
        Log.i(TAG, "获取所有活动信息的列表 Net");
        return activitiesRepository.getAllActivitiesNet();
    }

    public LiveData<Activities> getActivitiesLiveById(int activitiesId){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getActivitiesById(activitiesId);
    }

    public LiveData<Activities> getActivitiesLiveByIdNet(int activitiesId){
        Log.i(TAG, "获取所有活动信息的列表 Net");
        return activitiesRepository.getActivitiesByIdNet(activitiesId);
    }

    public void updateActivities(Activities activities){
        Log.i(TAG, "更新活动信息");
        activitiesRepository.updateActivities(activities);
    }

    public void updateActivitiesNet(Activities activities){
        Log.i(TAG, "更新活动信息 Net");
        activitiesRepository.updateActivitiesNet(activities);
    }


    //ActivitiesEnroll
    public LiveData<ActivitiesEnroll> getActivitiesEnrollByUserAccountAndActivitiesId(int userAccount,int activitiesId){
        Log.i(TAG, "通过UserId 以及 参数中的ActivitiesID 查找一个 活动报名记录 ");

        return aERepository.getActivitiesEnrollLive(userAccount,activitiesId);
    }
    public LiveData<ActivitiesEnroll> getActivitiesEnrollByUserAccountAndActivitiesIdNet(int userAccount,int activitiesId){
        Log.i(TAG, "通过UserId 以及 参数中的ActivitiesID 查找一个 活动报名记录 Net ");
        return aERepository.getActivitiesEnrollLiveNet(userAccount,activitiesId);
    }

    public LiveData<List<User>> queryActMemberList(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为2（报名成功）的用户(Member) ");
        return userRepository.queryActMemberList(activitiesId);
    }

    public LiveData<List<User>> queryActMemberListNet(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为2（报名成功）的用户(Member) Net");
        return userRepository.queryActMemberListNet(activitiesId);
    }


    public LiveData<List<User>> queryActEnrollMemberList(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为1（报名待审核）的用户(Enroll) ");
        return userRepository.queryActEnrollMemberList(activitiesId);
    }

    public LiveData<List<User>> queryActEnrollMemberListNet(int activitiesId){
        Log.i(TAG, "通过活动id查询报名活动状态为1（报名待审核）的用户(Enroll) Net");
        return userRepository.queryActEnrollMemberListNet(activitiesId);
    }

    public void addAE(ActivitiesEnroll ae){
        aERepository.addActivitiesEnroll(ae);
    }

    public void addAENet(ActivitiesEnroll ae){
        aERepository.addActivitiesEnrollNet(ae);
    }

    public void updateAE(ActivitiesEnroll ae){
        Log.i(TAG, "更新报名记录");
        aERepository.updateActivitiesEnroll(ae);
    }

    public void updateAENet(ActivitiesEnroll ae){
        Log.i(TAG, "更新报名记录 Net");
        aERepository.updateActivitiesEnrollNet(ae);
    }


    //Organization
    public LiveData<List<Organization>> getAllOrganizationListLive(){
        Log.i(TAG, "获取所有学生组织 ");
        return organizationRepository.getAllOrganization();
    }

    public LiveData<List<Organization>> getAllOrganizationListLiveNet(){
        Log.i(TAG, "获取所有学生组织 Net");
        return organizationRepository.getAllOrganizationNet();
    }

    public LiveData<Organization> getOrganizationLiveById(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 ");
        return organizationRepository.queryOrganizationById(organizationId);
    }

    public LiveData<Organization> getOrganizationLiveByIdNet(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 Net");
        return organizationRepository.queryOrganizationByIdNet(organizationId);
    }
    //OrganizationEnroll
    public LiveData<OrganizationEnroll> getOrganizationEnrollLive(int userAccount,int organizationId){
        Log.i(TAG, "通过UserId 以及 OrganizationId 查找一个学生组织报名记录 ");
        return oERepository.queryOrganizationEnrollLive(userAccount,organizationId);
    }

    public LiveData<OrganizationEnroll> getOrganizationEnrollLiveNet(int userAccount,int organizationId){
        Log.i(TAG, "通过UserId 以及 OrganizationId 查找一个学生组织报名记录 Net");
        return oERepository.queryOrganizationEnrollLiveNet(userAccount,organizationId);
    }

    public LiveData<List<User>> queryOrgMemberList(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为2 的报名信息 ");
        return userRepository.queryOrgMemberList(organizationId);
    }

    public LiveData<List<User>> queryOrgMemberListNet(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为2 的报名信息 Net");
        return userRepository.queryOrgMemberListNet(organizationId);
    }

    public LiveData<List<User>> queryOrgEnrollMemberList(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为1 的报名信息 ");
        return userRepository.queryOrgEnrollMemberList(organizationId);
    }

    public LiveData<List<User>> queryOrgEnrollMemberListNet(int organizationId){
        Log.i(TAG, "查询某学生组织所有报名状态为1 的报名信息 Net");
        return userRepository.queryOrgEnrollMemberListNet(organizationId);
    }

    public void addOE(OrganizationEnroll oe){
        Log.i(TAG, "新增学生组织报名记录");
        oERepository.addOE(oe);
    }

    public void addOENet(OrganizationEnroll oe){
        Log.i(TAG, "新增学生组织报名记录 Net");
        oERepository.addOENet(oe);
    }

    public void updateOE(OrganizationEnroll oe){
        Log.i(TAG, "更新学生组织报名记录");
        oERepository.updateOE(oe);
    }

    public void updateOENet(OrganizationEnroll oe){
        Log.i(TAG, "更新学生组织报名记录 Net");
        oERepository.updateOENet(oe);
    }

    //Department
    public LiveData<Department> getDepartmentLiveById(int departmentId){
        Log.i(TAG, "通过departmentId 获取部门信息 ");
        return departmentRepository.getDepartmentLiveById(departmentId);
    }

    public LiveData<Department> getDepartmentLiveByIdNet(int departmentId){
        Log.i(TAG, "通过departmentId 获取部门信息 Net");
        return departmentRepository.getDepartmentLiveByIdNet(departmentId);
    }

    public void updateOrganization(Organization organization){
        Log.i(TAG, "更新学生组织信息 ");
        organizationRepository.updateOrganization(organization);
    }

    public void updateOrganizationNet(Organization organization){
        Log.i(TAG, "更新学生组织信息 Net");
        organizationRepository.updateOrganizationNet(organization);
    }

    //Major
    public LiveData<Major> getMajorLiveById(int majorId){
        Log.i(TAG, "majorId 获取专业信息 ");
        return majorRepository.getMajorById(majorId);
    }

    public LiveData<Major> getMajorLiveByIdNet(int majorId){
        Log.i(TAG, "majorId 获取专业信息 Net");
        return majorRepository.getMajorByIdNet(majorId);
    }

}

