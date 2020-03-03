package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyOrganizationMemberFragmentPagerAdapter;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationMemberPage extends AppCompatActivity {

    private StudentUnionViewModel mViewModel;
    private MyOrganizationMemberFragmentPagerAdapter myOrganizationMemberFragmentPagerAdapter;
    private ViewPager2 mViewPager2;
    private TabLayoutMediator mTabLayoutMediator;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_union_organization_member_page);
        Intent intent = getIntent();
        int organizationId = intent.getIntExtra("organization_id",-1);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
//        mViewModel.setMemberList(organizationId);
//        mViewModel.setMemberEnrollList(organizationId);
        mViewModel.setOrganizationId(organizationId);
        mViewPager2 = findViewById(R.id.student_union_organization_member_viewPager2);
        mTabLayout = findViewById(R.id.student_union_organization_member_tabLayout);
        myOrganizationMemberFragmentPagerAdapter = new MyOrganizationMemberFragmentPagerAdapter(this);
        mViewPager2.setAdapter(myOrganizationMemberFragmentPagerAdapter);
        mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.tab_member);
                        break;
                    case 1:
                        tab.setText(R.string.tab_enroll_member);
                        break;
//                    default:
//                        tab.setText(R.string.tab_activities);
//                        break;
                }
            }
        });
        mTabLayoutMediator.attach();
    }
}
