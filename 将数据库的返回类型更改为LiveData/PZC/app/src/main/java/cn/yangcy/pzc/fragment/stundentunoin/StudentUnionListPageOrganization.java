package cn.yangcy.pzc.fragment.stundentunoin;

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

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyOrganizationRecyclerViewAdapter;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class StudentUnionListPageOrganization extends Fragment {

    private static final String TAG = "SU_OrgListPage";
    private StudentUnionViewModel mViewModel;
    private View view;
    private RecyclerView mRecyclerView;
    private MyOrganizationRecyclerViewAdapter myOrganizationRecyclerViewAdapter;
    public static StudentUnionListPageOrganization ORGANIZATION_LIST_INSTANCE;

    public static StudentUnionListPageOrganization newInstance() {
        return new StudentUnionListPageOrganization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ORGANIZATION_LIST_INSTANCE = this;
        view = inflater.inflate(R.layout.student_union_organization_list_page_fragment, container, false);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(StudentUnionViewModel.class);
        mRecyclerView = view.findViewById(R.id.student_union_organization_recyclerView);
        myOrganizationRecyclerViewAdapter = new MyOrganizationRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(myOrganizationRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
