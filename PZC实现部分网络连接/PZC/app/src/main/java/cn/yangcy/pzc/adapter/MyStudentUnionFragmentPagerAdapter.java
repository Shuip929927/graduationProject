package cn.yangcy.pzc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cn.yangcy.pzc.fragment.stundentunoin.StudentUnionListPageActivities;
import cn.yangcy.pzc.fragment.stundentunoin.StudentUnionListPageOrganization;

public class MyStudentUnionFragmentPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "MyStudentUnionFragmentPagerAdapter";
    
    public MyStudentUnionFragmentPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
//            case 0:
//                return StudentUnionListPageActivities.newInstance();
            case 1:
                return StudentUnionListPageOrganization.newInstance();
            default:
                return StudentUnionListPageActivities.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
