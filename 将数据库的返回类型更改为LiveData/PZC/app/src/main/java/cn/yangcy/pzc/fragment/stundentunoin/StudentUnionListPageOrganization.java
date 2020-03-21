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

import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyOrganizationRecyclerViewAdapter;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class StudentUnionListPageOrganization extends Fragment {

    private static final String TAG = "SU_OrgListPage";
    private StudentUnionViewModel mViewModel;
    private MyOrganizationRecyclerViewAdapter mAdapter;
    public static StudentUnionListPageOrganization INSTANCE;

    public static StudentUnionListPageOrganization newInstance() {
        return new StudentUnionListPageOrganization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        INSTANCE = this;
        View view = inflater.inflate(R.layout.student_union_organization_list_page_fragment, container, false);
        mViewModel = new ViewModelProvider(INSTANCE).get(StudentUnionViewModel.class);
        RecyclerView mRecyclerView = view.findViewById(R.id.student_union_organization_recyclerView);
        mAdapter = new MyOrganizationRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
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
        LiveData<List<Organization>> organizationListLive = mViewModel.getAllOrganizationListLive();
        organizationListLive.observe(Objects.requireNonNull(getActivity()), new Observer<List<Organization>>() {
            @Override
            public void onChanged(List<Organization> organizations) {
                mAdapter.setOrganizationList(organizations);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
