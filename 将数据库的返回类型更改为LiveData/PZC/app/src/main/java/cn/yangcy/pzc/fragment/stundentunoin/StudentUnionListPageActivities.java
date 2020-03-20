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
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyActivitiesRecyclerViewAdapter;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class StudentUnionListPageActivities extends Fragment {

    private static final String TAG = "SU_ActivitiesListPage";
    private StudentUnionViewModel mViewModel;
    private MyActivitiesRecyclerViewAdapter myActivitiesRecyclerViewAdapter;

    private LiveData<List<Activities>> allActivitiesLive;


    public static StudentUnionListPageActivities newInstance() {
        return new StudentUnionListPageActivities();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_union_activities_list_page_fragment, container, false);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);
        RecyclerView mRecyclerView = view.findViewById(R.id.student_union_activities_recyclerView);
        myActivitiesRecyclerViewAdapter = new MyActivitiesRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(myActivitiesRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        allActivitiesLive = mViewModel.getAllActivitiesListLive();
        allActivitiesLive.observe(Objects.requireNonNull(getActivity()), new Observer<List<Activities>>() {
            @Override
            public void onChanged(List<Activities> activities) {
                myActivitiesRecyclerViewAdapter.setActivitiesList(activities);
                myActivitiesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
