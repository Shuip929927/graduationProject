package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;


import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationRepository;
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
        infoListLive = informationRepository.getInfoListLive();
        infoPagedList = informationRepository.getInfoPagedList();
        sharedPreferences = application.getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);
    }

    public int getUserAccount() {
        return sharedPreferences.getInt("user_account", -1);
    }

    public int getUserPower() {
        return sharedPreferences.getInt("user_power", -1);
    }

    public void setClickInformation(Information clickInfo) {
//        这是通过传递点击的position获取所点击的信息，后修改为直接传递点击的信息类
//        Log.i(TAG, "setClickPosition: " + clickPosition);
//        clickInfo = infoListLive.getValue().get(clickPosition);
        informationRepository.addInformationHit(clickInfo);
        this.clickInfo = clickInfo;
//        return clickInfo;
    }

    public Information getClickInfo() {
//        informationRepository.addInformationHit(clickInfo);
        return clickInfo;
    }

    public LiveData<List<Information>> getInfoListLive() {
        return infoListLive;
    }

    public LiveData<PagedList<Information>> getInfoPagedList() {
        return infoPagedList;
    }


    public User getUser(int userAccount){
        return userRepository.queryUser(String.valueOf(userAccount));
    }

    public String getPersonInfo(int userAccount) {
        User user = userRepository.queryUser(String.valueOf(userAccount));
        String year = user.getYear();
        String department = departmentRepository.getDepartmentNameById(user.getDepartmentId());
        String major = majorRepository.getMajorNameById(user.getMajorId());
        return department + "\n" + year + major + " " + user.getName();
    }

    //TODO:测试MutableLiveData 转换成LiveData 输出 成功！
//    public LiveData<User> toastUser(){
//       return userRepository.getTestTest();
//    }
}
