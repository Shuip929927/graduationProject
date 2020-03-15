package cn.yangcy.pzc.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.fragment.stundentunoin.AtyStudentUnionActivitiesDetailPage;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class MyActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<MyActivitiesRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyActivitiesAdapter";
    private StudentUnionViewModel mStudentUnionViewModel;
    private MyPageViewModel myPageViewModel;
    private List<Activities> activitiesList = new ArrayList<>();
    private static String infoCreateTime;
    private static String infoStartTime;
    private static String infoHoldOrganization;
    public String flag = "";

    public MyActivitiesRecyclerViewAdapter(MyPageViewModel myPageViewModel) {
        this.myPageViewModel = myPageViewModel;
        flag = "myPage";
    }

    public MyActivitiesRecyclerViewAdapter(StudentUnionViewModel mStudentUnionViewModel) {
        this.mStudentUnionViewModel = mStudentUnionViewModel;
        flag = "mStu";
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
        infoCreateTime = parent.getContext().getResources().getString(R.string.info_create_time);
        infoStartTime = parent.getContext().getResources().getString(R.string.info_start_time);
        infoHoldOrganization = parent.getContext().getResources().getString(R.string.info_hold_organization);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Activities activities = activitiesList.get(position);
        holder.tv_title.setText(activities.getName());
        holder.tv_createTime.setText(infoCreateTime + " "+ activities.getCreateOn());
        holder.tv_startTime.setText(infoStartTime +" "+ activities.getStartTime());
        if("mStu".equals(flag)){
            holder.tv_holdOrganization.setText(infoHoldOrganization +" "+mStudentUnionViewModel.getActivitiesHoldOrgNameById(activities.getOrganizationId()));
        } else if("myPage".equals(flag)){
            holder.tv_holdOrganization.setText(infoHoldOrganization +" "+myPageViewModel.getActivitiesHoldOrgNameById(activities.getOrganizationId()));
        }


        int state = activities.getState();
        if(state == 0){
            holder.bt_activitiesState.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
            holder.bt_activitiesState.setText(R.string.info_activities_state_ongoing);
        } else if(state == 1){
            holder.bt_activitiesState.setBackgroundResource(R.drawable.btn_state_end);
            holder.bt_activitiesState.setText(R.string.info_activities_state_end);
        } else if(state == 2){
            holder.bt_activitiesState.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
            holder.bt_activitiesState.setText(R.string.info_activities_state_long);
        } else {
            holder.bt_activitiesState.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), AtyStudentUnionActivitiesDetailPage.class);
                detail.putExtra("activities_id", activities.getId());
                holder.itemView.getContext().startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title,tv_createTime,tv_startTime,tv_holdOrganization;
        private Button bt_activitiesState;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_student_union_activities_title);
            tv_createTime = itemView.findViewById(R.id.tv_student_union_activities_createTime);
            tv_startTime = itemView.findViewById(R.id.tv_student_union_activities_startTime);
            tv_holdOrganization = itemView.findViewById(R.id.tv_student_union_activities_organization);
            bt_activitiesState = itemView.findViewById(R.id.bt_student_union_activities_state);
        }
    }
}
