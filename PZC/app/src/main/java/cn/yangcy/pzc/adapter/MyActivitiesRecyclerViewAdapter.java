package cn.yangcy.pzc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.viewmodel.StudemtUnionViewModel;

public class MyActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<MyActivitiesRecyclerViewAdapter.MyViewHolder> {

    private StudemtUnionViewModel mViewModel;
    private List<Activities> activitiesList = new ArrayList<>();
    private static String infoStartTime;
    private static String infoHoldOrganization;
    private String TAG = "MyActivitiesRecyclerViewAdapter";

    public MyActivitiesRecyclerViewAdapter(StudemtUnionViewModel studemtUnionViewModel) {
        this.mViewModel = studemtUnionViewModel;
    }

    public void setActivitiesList(List<Activities> activitiesList) {
        this.activitiesList = activitiesList;
        for (int i = 0; i < activitiesList.size(); i++) {
            Log.i(TAG, "setActivitiesList: " + activitiesList.toString());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_activities_cell, parent, false);
        infoStartTime = parent.getContext().getResources().getString(R.string.info_start_time);
        infoHoldOrganization = parent.getContext().getResources().getString(R.string.info_hold_organization);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Activities activities = activitiesList.get(position);
        holder.tv_title.setText(activities.getName());
        holder.tv_startTime.setText(infoStartTime +" "+ activities.getStartTime());
        holder.tv_holdOrganization.setText(infoHoldOrganization +" "+activities.getOrganizationName());
        int state = activities.getState();
        if(state == 0){
            holder.bt_activitiesState.setText(R.string.info_activities_state_ongoing);
        } else if(state == 1){
            holder.bt_activitiesState.setBackgroundColor(R.drawable.btn_student_union_activities_state_end);
            holder.bt_activitiesState.setText(R.string.info_activities_state_end);
        } else {
            holder.bt_activitiesState.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title,tv_startTime,tv_holdOrganization;
        private Button bt_activitiesState;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_student_union_activities_title);
            tv_startTime = itemView.findViewById(R.id.tv_student_union_activities_startTime);
            tv_holdOrganization = itemView.findViewById(R.id.tv_student_union_activities_organization);
            bt_activitiesState = itemView.findViewById(R.id.bt_student_union_activities_state);
        }
    }
}
