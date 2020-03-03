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

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyStudentUnionFragmentPagerAdapter;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class StudentUnionListPage extends Fragment {

    private static final String TAG = "SUListPage";
    private View view;
    private StudentUnionViewModel mViewModel;

    private MyStudentUnionFragmentPagerAdapter myStudentUnionFragmentPagerAdapter;
    private ViewPager2 mViewPager2;
    private TabLayoutMediator mTabLayoutMediator;
    private TabLayout mTabLayout;

    public static StudentUnionListPage newInstance() {
        return new StudentUnionListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_union_list_page_fragment, container, false);
        mViewPager2 = view.findViewById(R.id.student_union_viewPager2);
        mTabLayout = view.findViewById(R.id.student_union_tabLayout);
        myStudentUnionFragmentPagerAdapter = new MyStudentUnionFragmentPagerAdapter(this);
        mViewPager2.setAdapter(myStudentUnionFragmentPagerAdapter);

        mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
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
        mViewModel = new ViewModelProvider(getActivity()).get(StudentUnionViewModel.class);
        // TODO: Use the ViewModel
    }

}
