package cn.yangcy.pzc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cn.yangcy.pzc.fragment.stundentunoin.OrganizationEnrollPage;
import cn.yangcy.pzc.fragment.stundentunoin.OrganizationMemberListPage;

public class MyOrganizationMemberFragmentPagerAdapter extends FragmentStateAdapter {

    private int count = 2;

    public MyOrganizationMemberFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
                return OrganizationMemberListPage.newInstance();
            default:
                return OrganizationEnrollPage.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
