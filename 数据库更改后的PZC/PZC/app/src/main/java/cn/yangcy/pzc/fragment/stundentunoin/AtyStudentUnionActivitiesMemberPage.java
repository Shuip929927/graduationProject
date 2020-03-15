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
import cn.yangcy.pzc.adapter.MyActivitiesMemberFragmentPagerAdapter;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesMemberPage extends AppCompatActivity {

    private StudentUnionViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_union_activities_member_page);
        Intent intent = getIntent();
        int activitiesId = intent.getIntExtra("activities_id",-1);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        mViewModel.setActivitiesId(activitiesId);

        ViewPager2 mViewPager2 = findViewById(R.id.student_union_activities_member_viewPager2);
        TabLayout mTabLayout = findViewById(R.id.student_union_activities_member_tabLayout);
        MyActivitiesMemberFragmentPagerAdapter myActivitiesMemberFragmentPagerAdapter = new MyActivitiesMemberFragmentPagerAdapter(this);
        mViewPager2.setAdapter(myActivitiesMemberFragmentPagerAdapter);

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
//                    default:
//                        tab.setText(R.string.tab_activities);
//                        break;
                }
            }
        });
        mTabLayoutMediator.attach();
    }
}
