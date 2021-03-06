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
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class ActivitiesEnrollPage extends Fragment {

    private static final String TAG = "ActivitiesEnrollPage";
    public static ActivitiesEnrollPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private MyMemberEnrollRecyclerViewAdapter mAdapter;

    private int i = 0;

    public static ActivitiesEnrollPage newInstance() {
        return new ActivitiesEnrollPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        INSTANCE = this;
        View view = inflater.inflate(R.layout.member_enroll_page_fragment, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.member_enroll_recyclerView);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);
        mAdapter = new MyMemberEnrollRecyclerViewAdapter("activities",mViewModel);
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
        mViewModel.getActivitiesEnrollMemberListLive(mViewModel.getActivitiesId()).observe(INSTANCE, new Observer<List<ActivitiesEnroll>>() {
            @Override
            public void onChanged(List<ActivitiesEnroll> activitiesEnrolls) {
                List<Integer> enrollUserAccountList = new ArrayList<>();
                for (int i = 0; i < activitiesEnrolls.size(); i++) {
                    enrollUserAccountList.add(activitiesEnrolls.get(i).getUserAccount());
                    Log.i(TAG, "onChanged: "+ activitiesEnrolls.size()+ activitiesEnrolls.get(i).toString());
                }
                LiveData<List<User>> enrollUserListLive = mViewModel.queryUserLiveData(enrollUserAccountList);
                enrollUserListLive.observe(INSTANCE, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        Log.i(TAG, "state 1 :" + i++);
                        mAdapter.setMemberEnrollList(users);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

}
