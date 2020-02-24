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
import cn.yangcy.pzc.adapter.MyActivitiesRecyclerViewAdapter;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.viewmodel.StudemtUnionViewModel;

public class StudentUnionListPageActivities extends Fragment {

    private View view;
    private StudemtUnionViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MyActivitiesRecyclerViewAdapter myActivitiesRecyclerViewAdapter;

    public static StudentUnionListPageActivities newInstance() {
        return new StudentUnionListPageActivities();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_union_list_page_activities_fragment, container, false);
        //        mViewModel = ViewModelProviders.of(this).get(StudemtUnionViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(StudemtUnionViewModel.class);
        mRecyclerView = view.findViewById(R.id.student_union_activities_recycleView);
        myActivitiesRecyclerViewAdapter = new MyActivitiesRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(myActivitiesRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        mViewModel.getAllActivitiesLiveList().observe(getViewLifecycleOwner(), new Observer<List<Activities>>() {
            @Override
            public void onChanged(List<Activities> activities) {
                myActivitiesRecyclerViewAdapter.setActivitiesList(activities);
                myActivitiesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

}
