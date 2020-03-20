package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyStudentUnionFragmentPagerAdapter;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class StudentUnionListPage extends Fragment {

    private static final String TAG = "SUListPage";

    public static StudentUnionListPage newInstance() {
        return new StudentUnionListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_union_list_page_fragment, container, false);
        ViewPager2 mViewPager2 = view.findViewById(R.id.student_union_viewPager2);
        TabLayout mTabLayout = view.findViewById(R.id.student_union_tabLayout);
        MyStudentUnionFragmentPagerAdapter myStudentUnionFragmentPagerAdapter = new MyStudentUnionFragmentPagerAdapter(this);
        mViewPager2.setAdapter(myStudentUnionFragmentPagerAdapter);

        //                    default:
        //                        tab.setText(R.string.tab_activities);
        //                        break;
        TabLayoutMediator mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.tab_activities);
                        break;
                    case 1:
                        tab.setText(R.string.tab_organization);
                        break;
//                    default:
//                        tab.setText(R.string.tab_activities);
//                        break;
                }
            }
        });
        mTabLayoutMediator.attach();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);
        StudentUnionViewModel mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);
    }

}
