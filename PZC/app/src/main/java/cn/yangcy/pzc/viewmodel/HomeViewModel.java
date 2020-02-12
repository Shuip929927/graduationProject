package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationRepository;

public class HomeViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG ="HomeViewModel";
    private InformationRepository informationRepository;
    private LiveData<List<Information>> infoListLive;
    private Information clickInfo;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        informationRepository = new InformationRepository(application);
        infoListLive = informationRepository.getInfoListLive();
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

}
