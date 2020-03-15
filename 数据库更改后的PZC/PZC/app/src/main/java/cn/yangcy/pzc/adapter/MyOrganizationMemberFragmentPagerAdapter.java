package cn.yangcy.pzc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cn.yangcy.pzc.fragment.stundentunoin.OrganizationEnrollPage;
import cn.yangcy.pzc.fragment.stundentunoin.OrganizationMemberListPage;

public class MyOrganizationMemberFragmentPagerAdapter extends FragmentStateAdapter {

    public MyOrganizationMemberFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
//            case 0:
//                return
            case 1:
                return OrganizationEnrollPage.newInstance();
            default:
                return OrganizationMemberListPage.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
