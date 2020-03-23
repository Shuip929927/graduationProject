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
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyEnrollOrganizationPage extends AppCompatActivity {

    private static final String TAG = "MyEnrollOrgPage";
    public static MyEnrollOrganizationPage INSTANCE;
    private MyEnrollOrganizationRecyclerViewAdapter mAdapter;
    private boolean isOrganizationCharge = false;
    private MutableLiveData<Integer> chargeOrgId = new MutableLiveData<>();
//    private int chargeOrgId;

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
        if(user.getPower()>2){
            Log.i(TAG, "权限大于2");
            mViewModel.getAllOrganizationListLive().observe(INSTANCE, new Observer<List<Organization>>() {
                @Override
                public void onChanged(List<Organization> organizations) {
                    for (int i = 0; i < organizations.size(); i++) {
                        Log.i(TAG, "负责部门查询"+i);
                        if(organizations.get(i).getPersonAccount() == user.getAccount()){
                            isOrganizationCharge = true;
                            chargeOrgId.setValue(organizations.get(i).getId());
//                            chargeOrgId = organizations.get(i).getId();
                            break;
                        }
                    }
                }
            });
        }
        mViewModel.queryUserEnrollOrganizationList(user.getAccount()).observe(INSTANCE, new Observer<List<OrganizationEnroll>>() {
            @Override
            public void onChanged(List<OrganizationEnroll> organizationEnrolls) {
                List<Integer> enrollOrgIdList = new ArrayList<>();
                for (int i = 0; i < organizationEnrolls.size(); i++) {
                    enrollOrgIdList.add(organizationEnrolls.get(i).getOrganizationId());
                    Log.i(TAG, "将用户报名学生组织成功的信息转为学生组织Id的list列表 "+ organizationEnrolls.size()+ organizationEnrolls.get(i).toString());
                }
                if(isOrganizationCharge){
                    Log.i(TAG, "是部门负责人,部门ID：" + chargeOrgId.getValue());
                    enrollOrgIdList.add(chargeOrgId.getValue());
//                    Log.i(TAG, "是部门负责人,部门ID：" + chargeOrgId);
//                    enrollOrgIdList.add(chargeOrgId);
                }
                mViewModel.queryUserEnrollOrganization(enrollOrgIdList).observe(INSTANCE, new Observer<List<Organization>>() {
                    @Override
                    public void onChanged(List<Organization> organizations) {
                        Log.i(TAG, "提示我加入的学生组织更新");
                        mAdapter.setOrganizationList(organizations);
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
}
