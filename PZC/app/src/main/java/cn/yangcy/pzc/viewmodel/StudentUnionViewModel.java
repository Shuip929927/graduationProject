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
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.enroll.EnrollRepository;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class StudentUnionViewModel extends AndroidViewModel {

    private static final String TAG = "StudentUnionViewModel";
    private OrganizationRepository organizationRepository;
    private UserRepository userRepository;
    private ActivitiesRepository activitiesRepository;
    private EnrollRepository enrollRepository;
    private LiveData<List<Activities>> activitiesLiveList;
    private LiveData<List<Organization>> organizationLiveList;
    private SharedPreferences sharedPreferences;
    private int userAccount;
    private int userPower;
    private int organizationId;
    private List<Integer> memberList;
    private List<Integer> memberEnrollList;
    private int userEnrollOrganizationNum;
    private int organizationMemberNum;

    private LiveData<Activities> activitiesLiveData;
    private LiveData<ActivitiesEnroll> activitiesEnrollLive;

    private LiveData<Organization> organizationLiveData;
    private LiveData<OrganizationEnroll> organizationEnrollLive;
    private LiveData<List<User>> organizationMember;

    public StudentUnionViewModel(@NonNull Application application) {
        super(application);
        organizationRepository = new OrganizationRepository(getApplication());
        userRepository = new UserRepository(getApplication());
        enrollRepository = new EnrollRepository(getApplication());
        activitiesRepository = new ActivitiesRepository(getApplication());
        activitiesLiveList = activitiesRepository.getAllActivities();
        organizationLiveList = organizationRepository.getAllOrganization();
        sharedPreferences = application.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);

    }
    // TODO: Implement the ViewModel


    public LiveData<List<Activities>> getAllActivitiesLiveList() {
        return activitiesLiveList;
    }

    public LiveData<List<Organization>> getAllOrganizationLiveList() {
        return organizationLiveList;
    }

    public String getOrganizationPersonInChargeName(int personInChargeAccount) {
        return userRepository.getOrganizationPersonInChargeName(personInChargeAccount);
    }

    public String getOrganizationMemberInfo(int account){
        return userRepository.getOrganizationMemberInfo(account);
    }

    public int getUserAccount() {
        userAccount = sharedPreferences.getInt("user_account", -1);
        return userAccount;
    }

    public int getUserPower() {
        userPower = sharedPreferences.getInt("user_power", -1);
        return userPower;
    }

    public LiveData<Activities> getActivitiesLiveData(int activitiesId) {
        activitiesLiveData = activitiesRepository.getActivitiesDetailLive(activitiesId);
        activitiesEnrollLive = enrollRepository.queryActivitiesEnrollState(getUserAccount(),activitiesId);
        return activitiesLiveData;
    }

    public LiveData<ActivitiesEnroll> getActivitiesEnroll(){
        return activitiesEnrollLive;
    }

    public void updateActivitiesEnroll(ActivitiesEnroll activitiesEnroll){
        enrollRepository.activitiesDoEnroll(activitiesEnroll);
    }

    public LiveData<Organization> getOrganizationLiveData(int organizationId) {
        organizationLiveData = organizationRepository.getOrganizationDetailLive(organizationId);
        organizationEnrollLive = enrollRepository.queryOrganizationEnrollState(getUserAccount(),organizationId);
        return organizationLiveData;
    }

    public LiveData<OrganizationEnroll> getOrganizationEnrollLive() {
        return organizationEnrollLive;
    }

    public void updateOrganizationEnroll(OrganizationEnroll organizationEnroll){
        enrollRepository.organizationDoEnroll(organizationEnroll);
    }

    public int getUserEnrollOrganizationNum() {
        userEnrollOrganizationNum = enrollRepository.getUserEnrollOrganizationNum(getUserAccount());
        return userEnrollOrganizationNum;
    }

    public int getOrganizationMemberNum(int organizationId) {
        organizationMemberNum = enrollRepository.getOrganizationMemberNum(organizationId);
        return organizationMemberNum;
    }

    public void updateOrganization(Organization organization){
        organizationRepository.updateOrganization(organization);
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public void setMemberList(int organizationId) {
        setOrganizationId(organizationId);
       memberList = enrollRepository.getMemberList(organizationId);
        Log.i(TAG, "setMemberList" + memberList.toString());
    }

    public List<Integer> getMemberList() {
        Log.i(TAG, "getMemberList");
        return memberList;
    }

    public List<Integer> getMemberEnrollList() {
        return memberEnrollList;
    }

    public void setMemberEnrollList(int organizationId) {
        memberEnrollList = enrollRepository.getMemberEnrollList(organizationId);
    }

    public LiveData<List<User>> getOrganizationMember(List<Integer> list) {
        organizationMember = userRepository.getUserLiveData(list);
        Log.i(TAG, "getOrganizationMember");
        return organizationMember;
    }



    public void updateMemberEnroll(boolean choose,User user,int power,int organizationId){
        if(choose){
            user.setPower(power);
            enrollRepository.updateOrganizationMemberEnroll(user.getAccount(),organizationId,2);
            userRepository.updateUserPower(user);
            organizationRepository.updateOrganizationPeopleNumber(organizationId);
        } else {
            enrollRepository.deleteOrganizationMemberEnroll(user.getAccount(),organizationId);
        }
    }
}

