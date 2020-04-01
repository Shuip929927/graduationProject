package cn.yangcy.pzc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesEnrollPage;
import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesMemberListPage;

public class MyActivitiesMemberFragmentPagerAdapter extends FragmentStateAdapter {

    private int count = 2;

    public MyActivitiesMemberFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ActivitiesMemberListPage.newInstance();
            default :
                return ActivitiesEnrollPage.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
