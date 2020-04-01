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

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyOrganizationMemberFragmentPagerAdapter;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationMemberPage extends AppCompatActivity {

    private static final String TAG = "AtySU_OrgMemberPage";
    private AtyStudentUnionOrganizationMemberPage INSTANCE;
    private MyOrganizationMemberFragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        setContentView(R.layout.student_union_organization_member_page);
        Intent intent = getIntent();
        int organizationId = intent.getIntExtra("organization_id", -1);

        final StudentUnionViewModel mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        Log.i(TAG, "向ViewModel 设置organizationId ");
        mViewModel.setOrganizationId(organizationId);
        mViewPager2 = findViewById(R.id.student_union_organization_member_viewPager2);
        mTabLayout = findViewById(R.id.student_union_organization_member_tabLayout);
        mAdapter = new MyOrganizationMemberFragmentPagerAdapter(this);

        //TODO 根据用户权限加载Tab(Organization) Fragment的个数
        if (mViewModel.getSpUserPower() < 3) {
            Log.i(TAG, "用户权限小于3 ");
            mAdapter.setCount(1);
            mViewPager2.setAdapter(mAdapter);
            TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.tab_member);
                            break;
                    }
                }
            });
            mTabLayoutMediator.attach();
        } else {
            final LiveData<Organization> organizationLive;
            if(NetWorkUtil.isConnected(getBaseContext())){
                organizationLive = mViewModel.getOrganizationLiveByIdNet(organizationId);
            } else {
                organizationLive = mViewModel.getOrganizationLiveById(organizationId);
            }
            organizationLive.observe(INSTANCE, new Observer<Organization>() {
                @Override
                public void onChanged(Organization organization) {
                    if (mViewModel.getSpUserPower()>3 ||mViewModel.getSpUserAccount() == organization.getPerson_id()) {
                        Log.i(TAG, "用户权限大于3 或用户为该部门负责人 ");
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
                        Log.i(TAG, "用户权限等于3 但不是该部门负责人 ");
                        mAdapter.setCount(1);
                        mViewPager2.setAdapter(mAdapter);
                        TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                            @Override
                            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                switch (position) {
                                    case 0:
                                        tab.setText(R.string.tab_member);
                                        break;
                                }
                            }
                        });
                        mTabLayoutMediator.attach();
                    }
                    organizationLive.removeObserver(this);
                }
            });
        }
    }
}
