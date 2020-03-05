package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;


import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class HomeViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG ="HomeViewModel";
    private InformationRepository informationRepository;
    private UserRepository userRepository;
    private LiveData<List<Information>> infoListLive;
    private Information clickInfo;
    private LiveData<PagedList<Information>> infoPagedList;
    private SharedPreferences sharedPreferences;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        informationRepository = new InformationRepository(application);
        userRepository = new UserRepository(application);
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

    public Information setClickInformation(Information clickInfo) {
//        这是通过传递点击的position获取所点击的信息，后修改为直接传递点击的信息类
//        Log.i(TAG, "setClickPosition: " + clickPosition);
//        clickInfo = infoListLive.getValue().get(clickPosition);
        informationRepository.addInformationHit(clickInfo);
        this.clickInfo = clickInfo;
        return clickInfo;
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
}
