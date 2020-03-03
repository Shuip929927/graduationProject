package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import cn.yangcy.pzc.adapter.MyOrganizationMemberEnrollRecyclerViewAdapter;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class OrganizationEnrollPage extends Fragment {

    private static final String TAG = "OrganizationEnrollPage";
    private StudentUnionViewModel mViewModel;
    private View view;
    private RecyclerView mRecyclerView;
    private MyOrganizationMemberEnrollRecyclerViewAdapter myOrganizationMemberEnrollRecyclerViewAdapter;
    private List<Integer> memberEnrollList;
    private LiveData<List<User>> memberEnrollLiveData;

    public static OrganizationEnrollPage newInstance() {
        return new OrganizationEnrollPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.organization_enroll_page_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.organization_member_enroll_recyclerView);
        mViewModel = new ViewModelProvider(getActivity()).get(StudentUnionViewModel.class);
        myOrganizationMemberEnrollRecyclerViewAdapter = new MyOrganizationMemberEnrollRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(myOrganizationMemberEnrollRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.setMemberEnrollList(mViewModel.getOrganizationId());
        memberEnrollList = mViewModel.getMemberEnrollList();
        memberEnrollLiveData = mViewModel.getOrganizationMember(memberEnrollList);
        memberEnrollLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                myOrganizationMemberEnrollRecyclerViewAdapter.setMemberEnrollList(users);
                myOrganizationMemberEnrollRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        mViewModel.setMemberEnrollList(mViewModel.getOrganizationId());
        memberEnrollList = mViewModel.getMemberEnrollList();
        memberEnrollLiveData = mViewModel.getOrganizationMember(memberEnrollList);
    }
}
