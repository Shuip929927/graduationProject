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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyShowMemberListRecyclerViewAdapter;
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class ActivitiesMemberListPage extends Fragment {

    private static final String TAG = "ActivitiesMemberListPag";
    public static ActivitiesMemberListPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private MyShowMemberListRecyclerViewAdapter mAdapter;
    private int i = 0;
    public static ActivitiesMemberListPage newInstance() {
        return new ActivitiesMemberListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        INSTANCE = this;
        View view = inflater.inflate(R.layout.show_member_list_page_fragment, container, false);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);

        RecyclerView mRecyclerView = view.findViewById(R.id.show_member_list_recyclerView);
        mAdapter = new MyShowMemberListRecyclerViewAdapter("activities",mViewModel);
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
        LiveData<List<User>> userListLive;
        if(NetWorkUtil.isConnected(getActivity())){
            userListLive = mViewModel.queryActMemberListNet(mViewModel.getActivitiesId());
        } else {
            userListLive = mViewModel.queryActMemberList(mViewModel.getActivitiesId());
        }

        userListLive.observe(INSTANCE, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mAdapter.setMemberList(users);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
