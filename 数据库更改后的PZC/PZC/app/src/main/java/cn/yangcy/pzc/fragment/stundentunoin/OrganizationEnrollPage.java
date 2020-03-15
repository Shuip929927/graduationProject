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
import cn.yangcy.pzc.adapter.MyMemberEnrollRecyclerViewAdapter;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class OrganizationEnrollPage extends Fragment {

    private static final String TAG = "OrganizationEnrollPage";
    private StudentUnionViewModel mViewModel;
    private MyMemberEnrollRecyclerViewAdapter myMemberEnrollRecyclerViewAdapter;

    public static OrganizationEnrollPage newInstance() {
        return new OrganizationEnrollPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_enroll_page_fragment, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.member_enroll_recyclerView);
        mViewModel = new ViewModelProvider(getActivity()).get(StudentUnionViewModel.class);
        myMemberEnrollRecyclerViewAdapter = new MyMemberEnrollRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(myMemberEnrollRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);
//        mViewModel.setMemberEnrollList(mViewModel.getOrganizationId());
//        memberEnrollList = mViewModel.getMemberEnrollList();
//        memberEnrollLiveData = mViewModel.getMemberList(memberEnrollList);
//        memberEnrollLiveData.observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                myMemberEnrollRecyclerViewAdapter.setMemberEnrollList(users);
//                myMemberEnrollRecyclerViewAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        mViewModel.setMemberEnrollList("organization_id",mViewModel.getOrganizationId());
        List<Integer> memberEnrollList = mViewModel.getMemberEnrollList();
        LiveData<List<User>> memberEnrollLiveData = mViewModel.getMemberList(memberEnrollList);
        memberEnrollLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                myMemberEnrollRecyclerViewAdapter.setMemberEnrollList("organization_id",users);
                myMemberEnrollRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
