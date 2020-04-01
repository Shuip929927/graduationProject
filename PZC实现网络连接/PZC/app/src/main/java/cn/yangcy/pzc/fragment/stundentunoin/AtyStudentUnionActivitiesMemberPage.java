package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.security.cert.LDAPCertStoreParameters;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyActivitiesMemberFragmentPagerAdapter;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesMemberPage extends AppCompatActivity {

    private static final String TAG = "AtySU_ActMemberPage";
    public static AtyStudentUnionActivitiesMemberPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private MyActivitiesMemberFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_union_activities_member_page);
        INSTANCE = this;
        Intent intent = getIntent();
        int activitiesId = intent.getIntExtra("activities_id", -1);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        Log.i(TAG, "向ViewModel传递ActivitiesId ");
        mViewModel.setActivitiesId(activitiesId);

        mViewPager2 = findViewById(R.id.student_union_activities_member_viewPager2);
        mTabLayout = findViewById(R.id.student_union_activities_member_tabLayout);
        mAdapter = new MyActivitiesMemberFragmentPagerAdapter(this);

        //TODO 根据用户权限加载Tab(Activities) Fragment的个数
        if (mViewModel.getSpUserPower() > 2) {
            final LiveData<Activities> activitiesLive;
            if(NetWorkUtil.isConnected(getBaseContext())){
                activitiesLive = mViewModel.getActivitiesLiveByIdNet(activitiesId);
            } else {
                activitiesLive = mViewModel.getActivitiesLiveById(activitiesId);
            }
            activitiesLive.observe(INSTANCE, new Observer<Activities>() {
                @Override
                public void onChanged(Activities activities) {
                    LiveData<Organization> orgLive;
                    if(NetWorkUtil.isConnected(getBaseContext())){
                        orgLive = mViewModel.getOrganizationLiveByIdNet(activities.getOrganization_id());
                    } else {
                        orgLive = mViewModel.getOrganizationLiveById(activities.getOrganization_id());
                    }
                    orgLive.observe(INSTANCE, new Observer<Organization>() {
                        @Override
                        public void onChanged(Organization organization) {
                            if (mViewModel.getSpUserPower()>3 || mViewModel.getSpUserAccount() == organization.getPerson_id()) {
                                Log.i(TAG, "用户权限大于3 或是该本活动举办部门的负责人 ");
                                mViewPager2.setAdapter(mAdapter);
                                TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                    @Override
                                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                        switch (position) {
                                            case 0:
                                                tab.setText(R.string.tab_member);
                                                break;
                                            case 1:
                                                tab.setText(R.string.tab_enroll_member);
                                                break;

                                        }
                                    }
                                });
                                mTabLayoutMediator.attach();
                            } else {
                                Log.i(TAG, "用户权限等于3 但不是该本活动举办部门的负责人 ");
                                mAdapter.setCount(1);
                                mViewPager2.setAdapter(mAdapter);
                                TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                    @Override
                                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                        switch (position) {
                                            case 0:
                                                tab.setText(R.string.tab_member);
                                        }
                                    }
                                });
                                mTabLayoutMediator.attach();
                            }
                        }
                    });
                    activitiesLive.removeObserver(this);
                }
            });
        } else {
            Log.i(TAG, "用户权限小于3 ");
            mAdapter.setCount(1);
            mViewPager2.setAdapter(mAdapter);
            TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.tab_member);
                    }
                }
            });
            mTabLayoutMediator.attach();
        }
    }
}
