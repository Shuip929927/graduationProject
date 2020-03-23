package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;


import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationRepository;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG ="HomeViewModel";
    private InformationRepository informationRepository;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;
    private LiveData<List<Information>> infoListLive;
    private Information clickInfo;
    private LiveData<PagedList<Information>> infoPagedList;
    private SharedPreferences sharedPreferences;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        informationRepository = new InformationRepository(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        majorRepository = new MajorRepository(application);
        Log.i(TAG, "获取Repository实例 ");
        infoListLive = informationRepository.getInfoListLive();
//        infoPagedList = informationRepository.getInfoPagedList();
        sharedPreferences = application.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);
    }

    public int getUserAccount() {
        Log.i(TAG, "从SP中获取账号 ");
        return sharedPreferences.getInt("user_account", -1);
    }

    public int getUserPower() {
        Log.i(TAG, "从SP中获取用户权限 ");
        return sharedPreferences.getInt("user_power", -1);
    }

    public void setClickInformation(Information clickInfo) {
        Log.i(TAG, "标记当前点击的信息 ");
        informationRepository.addInformationHit(clickInfo);
        this.clickInfo = clickInfo;
    }

    public void setClickInfoToNet(Information clickInfoToNet){
        Log.i(TAG, "标记当前点击的信息 ");
        informationRepository.addInformationHintsByNet(clickInfoToNet);
        this.clickInfo = clickInfoToNet;
    }

    public Information getClickInfo() {
        Log.i(TAG, "返回点击的信息 ");
        return clickInfo;
    }

    public LiveData<List<Information>> getInfoListLive() {
        Log.i(TAG, "获取信息列表 ");
        return infoListLive;
    }

    public LiveData<List<Information>> getInfoListByNet(){
        return informationRepository.queryInfoListByNet();
    }

    public LiveData<PagedList<Information>> getInfoPagedList() {
        Log.i(TAG, "获取分页信息列表 ");
        return infoPagedList;
    }
    public LiveData<User> getUser(int userAccount){
        Log.i(TAG, "通过UserId获取用户");
        return userRepository.queryUserByAccount(userAccount);
    }
    public LiveData<Department> getDepartmentLiveById(int departmentId) {
        Log.i(TAG, "通过部门ID获取部门信息");
        return departmentRepository.getDepartmentLiveById(departmentId);
    }

    public LiveData<Major> getMajorById(int majorId){
        return majorRepository.getMajorById(majorId);
    }
}
