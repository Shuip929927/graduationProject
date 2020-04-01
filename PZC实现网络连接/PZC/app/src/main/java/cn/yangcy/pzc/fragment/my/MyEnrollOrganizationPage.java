package cn.yangcy.pzc.fragment.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyEnrollOrganizationRecyclerViewAdapter;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyEnrollOrganizationPage extends AppCompatActivity {

    private static final String TAG = "MyEnrollOrgPage";
    public static MyEnrollOrganizationPage INSTANCE;
    private MyEnrollOrganizationRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        setContentView(R.layout.my_recycler_view_page);
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("user");
        final MyPageViewModel mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        RecyclerView mRecyclerView = findViewById(R.id.my_recyclerView);
        mAdapter = new MyEnrollOrganizationRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);
        LiveData<List<Organization>> orgListLive;
        if(NetWorkUtil.isConnected(INSTANCE)){
            orgListLive = mViewModel.queryUserEnrollOrganizationListNet(user.getAccount());
        } else {
            orgListLive = mViewModel.queryUserEnrollOrganizationList(user.getAccount());
        }
        orgListLive.observe(INSTANCE, new Observer<List<Organization>>() {
            @Override
            public void onChanged(List<Organization> organizations) {
                mAdapter.setOrganizationList(organizations);
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
