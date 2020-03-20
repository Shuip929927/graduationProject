package cn.yangcy.pzc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesEnrollPage;
import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesMemberListPage;

public class MyActivitiesMemberFragmentPagerAdapter extends FragmentStateAdapter {

    public MyActivitiesMemberFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return ActivitiesEnrollPage.newInstance();
            default:
                return ActivitiesMemberListPage.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
