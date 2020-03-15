package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.enroll.EnrollRepository;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
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
    private EnrollRepository enrollRepository;

    private int activitiesId;
    private LiveData<List<Activities>> activitiesLiveList;
    private LiveData<ActivitiesEnroll> activitiesEnrollLive;

    private int organizationId;
    private LiveData<List<Organization>> organizationLiveList;
    private LiveData<OrganizationEnroll> organizationEnrollLive;

    private List<Integer> memberList;
    private List<Integer> memberEnrollList;

    public StudentUnionViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);

        userRepository = new UserRepository(getApplication());
        departmentRepository = new DepartmentRepository(getApplication());
        majorRepository = new MajorRepository(getApplication());
        activitiesRepository = new ActivitiesRepository(getApplication());
        organizationRepository = new OrganizationRepository(getApplication());
        enrollRepository = new EnrollRepository(getApplication());

        activitiesLiveList = activitiesRepository.getAllActivities();
        organizationLiveList = organizationRepository.getAllOrganization();


    }

    public int getUserAccount() {
        return sharedPreferences.getInt("user_account", -1);
    }

    public int getUserPower() {
        return sharedPreferences.getInt("user_power", -1);
    }

//Activities

    public int getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(int activitiesId) {
        this.activitiesId = activitiesId;
    }

    public LiveData<List<Activities>> getAllActivitiesLiveList() {
        return activitiesLiveList;
    }

    public LiveData<Activities> getActivitiesLiveData(int activitiesId) {
        LiveData<Activities> activitiesLiveData = activitiesRepository.getActivitiesDetailLive(activitiesId);
        activitiesEnrollLive = enrollRepository.queryActivitiesEnrollState(getUserAccount(), activitiesId);
        return activitiesLiveData;
    }

    public LiveData<ActivitiesEnroll> getActivitiesEnroll() {
        return activitiesEnrollLive;
    }

    public void updateActivitiesEnroll(ActivitiesEnroll activitiesEnroll) {
        enrollRepository.activitiesDoEnroll(activitiesEnroll);
    }

    public void updateActivities(Activities activities) {
        activitiesRepository.updateActivities(activities);
    }

    private void updateActivitiesMemberEnroll(boolean choose, User user, int power, int activitiesId) {
        if (choose) {
            enrollRepository.updateActivitiesMemberEnroll(user.getAccount(), activitiesId, 2);
        } else {
            enrollRepository.deleteActivitiesMemberEnroll(user.getAccount(), activitiesId);
        }
    }

    public String getActivitiesHoldOrgNameById(int organizationId){
        Organization organization = organizationRepository.getOrganizationById(organizationId);
        return organization.getOrganization();
    }

    public int getOrganizationIdByName(String organizationName){
        return organizationRepository.getOrganizationIdByName(organizationName);
    }


//Organization

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public LiveData<List<Organization>> getAllOrganizationLiveList() {
        return organizationLiveList;
    }

    public String getOrganizationPersonInChargeName(int personInChargeAccount) {
        return userRepository.getOrganizationPersonInChargeName(personInChargeAccount);
    }

    public LiveData<Organization> getOrganizationLiveData(int organizationId) {
        LiveData<Organization> organizationLiveData = organizationRepository.getOrganizationDetailLive(organizationId);
        organizationEnrollLive = enrollRepository.queryOrganizationEnrollState(getUserAccount(), organizationId);
        return organizationLiveData;
    }

    public LiveData<OrganizationEnroll> getOrganizationEnrollLive() {
        return organizationEnrollLive;
    }

    public void updateOrganizationEnroll(OrganizationEnroll organizationEnroll) {
        enrollRepository.organizationDoEnroll(organizationEnroll);
    }

    public int getUserEnrollOrganizationNum() {
        return enrollRepository.getUserEnrollOrganizationNum(getUserAccount());
    }

    public void updateOrganization(Organization organization) {
        organizationRepository.updateOrganization(organization);
    }

    private void updateOrganizationMemberEnroll(boolean choose, User user, int power, int organizationId) {
        if (choose) {
            user.setPower(power);
            enrollRepository.updateOrganizationMemberEnroll(user.getAccount(), organizationId, 2);
            userRepository.updateUser(user);
            organizationRepository.updateOrganizationPeopleNumber(organizationId);
        } else {
            enrollRepository.deleteOrganizationMemberEnroll(user.getAccount(), organizationId);
        }
    }

    public int searchPersonInChargeOrgId(int userAccount) {
        return organizationRepository.queryPersonInChargeOrgid(userAccount);
    }

//Common

    public void setMemberList(String type, int id) {
        if ("organization_id".equals(type)) {
            memberList = enrollRepository.getOrganizationMemberList(id);
            Log.i(TAG, "getOrganizationMemberList" + memberList.toString());
        } else if ("activities_id".equals(type)) {
            memberList = enrollRepository.getActivitiesMemberList(id);
            Log.i(TAG, "getActivitiesMemberList" + memberList.toString());
        }

    }

    public List<Integer> getMemberList() {
        Log.i(TAG, "getOrganizationMemberList");
        return memberList;
    }

    public LiveData<List<User>> getMemberList(List<Integer> list) {
        LiveData<List<User>> memberList = userRepository.getUserLiveData(list);
        Log.i(TAG, "getMemberList");
        return memberList;
    }

    public List<Integer> getMemberEnrollList() {
        return memberEnrollList;
    }

    public void setMemberEnrollList(String type, int id) {
        if ("organization_id".equals(type)) {
            memberEnrollList = enrollRepository.getOrganizationMemberEnrollList(id);
        } else if ("activities_id".equals(type)) {
            memberEnrollList = enrollRepository.getActivitiesMemberEnrollList(id);
        }

    }

    public void updateMemberEnrollMessage(String type, boolean choose, User user, int power) {
        if ("organization_id".equals(type)) {
            updateOrganizationMemberEnroll(choose, user, power, getOrganizationId());
        } else if ("activities_id".equals(type)) {
            updateActivitiesMemberEnroll(choose, user, power, getActivitiesId());
        }
    }

    public String getPersonInfo(int userAccount) {
        User user = userRepository.queryUser(String.valueOf(userAccount));
        String year = user.getYear();
        String department = departmentRepository.getDepartmentNameById(user.getDepartmentId());
        String major = majorRepository.getMajorNameById(user.getMajorId());
        return department + "\n" + year + major + " " + user.getName();
    }

    public String getMemberInfo(int personInChargeAccount) {
        return userRepository.getOrganizationMemberInfo(personInChargeAccount);
    }
}

