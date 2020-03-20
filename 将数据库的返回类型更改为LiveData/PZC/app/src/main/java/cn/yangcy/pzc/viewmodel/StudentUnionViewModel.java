package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.enroll.ActivitiesEnrollRepository;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.enroll.OrganizationEnrollRepository;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;
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


    //Activities
    public LiveData<List<Activities>> getAllActivitiesListLive(){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getAllActivities();
    }

    public LiveData<Activities> getActivitiesLiveById(int activitiesId){
        Log.i(TAG, "获取所有活动信息的列表");
        return activitiesRepository.getActivitiesById(activitiesId);
    }



    //ActivitiesEnroll
    public LiveData<ActivitiesEnroll> getActivitiesEnrollByActivitiesId(int activitiesId){
        Log.i(TAG, "通过SP中的UserId 以及 参数中的ActivitiesID 查找一个 活动报名记录 ");
        return aERepository.getActivitiesEnrollLive(getSpUserAccount(),activitiesId);
    }

    public void addAE(ActivitiesEnroll ae){
        aERepository.addActivitiesEnroll(ae);
    }

    public void updateAE(ActivitiesEnroll ae){
        Log.i(TAG, "更新报名记录");
        aERepository.updateActivitiesEnroll(ae);
    }


    //Department
    public LiveData<Organization> getOrganizationLiveById(int organizationId){
        Log.i(TAG, "通过organizationId查找学生组织信息 ");
        return organizationRepository.queryOrganizationById(organizationId);
    }
}

