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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyMemberEnrollRecyclerViewAdapter;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class OrganizationEnrollPage extends Fragment {

    private static final String TAG = "OrganizationEnrollPage";
    public static OrganizationEnrollPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private MyMemberEnrollRecyclerViewAdapter mAdapter;

    private int i = 0;

    public static OrganizationEnrollPage newInstance() {
        return new OrganizationEnrollPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_enroll_page_fragment, container, false);
        INSTANCE = this;
        RecyclerView mRecyclerView = view.findViewById(R.id.member_enroll_recyclerView);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);
        mAdapter = new MyMemberEnrollRecyclerViewAdapter("organization",mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        LiveData<List<User>> userList;
        if(NetWorkUtil.isConnected(getContext())){
            userList = mViewModel.queryOrgEnrollMemberListNet(mViewModel.getOrganizationId());
        } else {
            userList = mViewModel.queryOrgEnrollMemberList(mViewModel.getOrganizationId());
        }
        userList.observe(INSTANCE, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mAdapter.setMemberEnrollList(users);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
