package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyOrganizationMemberListRecyclerViewAdapter;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class OrganizationMemberListPage extends Fragment {

    private static final String TAG = "OrganizationMemberListP";
    private StudentUnionViewModel mViewModel;
    private View view;
    private RecyclerView mRecyclerView;
    private MyOrganizationMemberListRecyclerViewAdapter myOrganizationMemberListRecyclerViewAdapter;
    private List<Integer> memberAccountList;
    private LiveData<List<User>> memberLiveData;

    public static OrganizationMemberListPage newInstance() {
        return new OrganizationMemberListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.organization_member_list_page_fragment, container, false);
        mViewModel = new ViewModelProvider(getActivity()).get(StudentUnionViewModel.class);
        mRecyclerView = view.findViewById(R.id.organization_member_list_recyclerView);
        myOrganizationMemberListRecyclerViewAdapter = new MyOrganizationMemberListRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(myOrganizationMemberListRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.setMemberList(mViewModel.getOrganizationId());
        memberAccountList = mViewModel.getMemberList();
        memberLiveData = mViewModel.getOrganizationMember(memberAccountList);
        memberLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: List<User> users  " + users.size());
                myOrganizationMemberListRecyclerViewAdapter.setMemberList(users);
                myOrganizationMemberListRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

}
