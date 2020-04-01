package cn.yangcy.pzc.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.fragment.my.MyEnrollActivitiesPage;
import cn.yangcy.pzc.fragment.my.MyHoldActivitiesPage;
import cn.yangcy.pzc.fragment.stundentunoin.AtyStudentUnionActivitiesDetailPage;
import cn.yangcy.pzc.fragment.stundentunoin.StudentUnionListPageActivities;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.util.NetWorkUtil;
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
    public String type;

    public MyActivitiesRecyclerViewAdapter(String type, MyPageViewModel myPageViewModel) {
        this.myPageViewModel = myPageViewModel;
        this.type = type;
    }

    public MyActivitiesRecyclerViewAdapter(String type, StudentUnionViewModel mStudentUnionViewModel) {
        this.mStudentUnionViewModel = mStudentUnionViewModel;
        this.type = type;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        final Activities activities = activitiesList.get(position);
        holder.tv_title.setText(activities.getActivity_name());
        holder.tv_createTime.setText(infoCreateTime +" "+ activities.getCreate_time());
        holder.tv_startTime.setText(infoStartTime +" "+ activities.getStart_time());
        if("mStuA_Page".equals(type)){
            LiveData<Organization> orgLive;
           if(NetWorkUtil.isConnected(Objects.requireNonNull(StudentUnionListPageActivities.INSTANCE.getContext()))){
               orgLive = mStudentUnionViewModel.getOrganizationLiveByIdNet(activities.getOrganization_id());
           } else {
               orgLive = mStudentUnionViewModel.getOrganizationLiveById(activities.getOrganization_id());
           }
           orgLive.observe(StudentUnionListPageActivities.INSTANCE, new Observer<Organization>() {
               @SuppressLint("SetTextI18n")
               @Override
               public void onChanged(Organization organization) {
                  if(organization != null){
                      holder.tv_holdOrganization.setText(infoHoldOrganization +" "+organization.getOrganization_name());
                  } else {
                      holder.tv_holdOrganization.setText(infoHoldOrganization +" 加载中");
                  }
               }
           });
        } else if("mHoldA_Page".equals(type)){
            LiveData<Organization> orgLive;
            if(NetWorkUtil.isConnected(MyHoldActivitiesPage.INSTANCE.getBaseContext())){
                orgLive = myPageViewModel.getOrganizationLiveByIdNet(activities.getOrganization_id());
            } else {
                orgLive = myPageViewModel.getOrganizationLiveById(activities.getOrganization_id());
            }
            orgLive.observe(MyHoldActivitiesPage.INSTANCE, new Observer<Organization>() {
                @Override
                public void onChanged(Organization organization) {
                    holder.tv_holdOrganization.setText(infoHoldOrganization +" "+organization.getOrganization_name());
                }
            });
        } else if("myEnrollA_Page".equals(type)) {
            LiveData<Organization> orgLive;
            if(NetWorkUtil.isConnected(MyEnrollActivitiesPage.INSTANCE.getBaseContext())){
               orgLive = myPageViewModel.getOrganizationLiveByIdNet(activities.getOrganization_id());

            } else {
                orgLive = myPageViewModel.getOrganizationLiveById(activities.getOrganization_id());
            }
            orgLive.observe(MyEnrollActivitiesPage.INSTANCE, new Observer<Organization>() {
                @Override
                public void onChanged(Organization organization) {
                    holder.tv_holdOrganization.setText(infoHoldOrganization + " " + organization.getOrganization_name());
                }
            });
        }

        int state = activities.getActivity_state();
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
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_student_union_activities_title);
            tv_createTime = itemView.findViewById(R.id.tv_student_union_activities_createTime);
            tv_startTime = itemView.findViewById(R.id.tv_student_union_activities_startTime);
            tv_holdOrganization = itemView.findViewById(R.id.tv_student_union_activities_organization);
            bt_activitiesState = itemView.findViewById(R.id.bt_student_union_activities_state);
        }
    }
}
