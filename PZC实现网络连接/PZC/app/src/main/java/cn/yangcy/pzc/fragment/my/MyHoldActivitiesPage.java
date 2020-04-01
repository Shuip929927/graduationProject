package cn.yangcy.pzc.fragment.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpCookie;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyActivitiesRecyclerViewAdapter;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyHoldActivitiesPage extends AppCompatActivity {

    private static final String TAG = "MyHoldActivitiesPage";
    public static MyHoldActivitiesPage INSTANCE;
    private MyActivitiesRecyclerViewAdapter mAdapter;
    private boolean isOrganizationCharge = false;
    private MutableLiveData<Integer> chargeOrgId = new MutableLiveData<>();
    private MyPageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        setContentView(R.layout.my_recycler_view_page);
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        RecyclerView mRecyclerView = findViewById(R.id.my_recyclerView);
        mAdapter = new MyActivitiesRecyclerViewAdapter("mHoldA_Page", mViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LiveData<List<Activities>> actListLive;
        if(NetWorkUtil.isConnected(INSTANCE)){
            actListLive = mViewModel.getOrganizationHoldActivitiesListNet(user.getAccount());
        } else {
            actListLive = mViewModel.getOrganizationHoldActivitiesList(user.getAccount());
        }
        actListLive.observe(INSTANCE, new Observer<List<Activities>>() {
            @Override
            public void onChanged(List<Activities> activities) {
                mAdapter.setActivitiesList(activities);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
