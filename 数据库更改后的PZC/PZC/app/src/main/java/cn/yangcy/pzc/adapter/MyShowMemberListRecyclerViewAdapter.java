package cn.yangcy.pzc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class MyShowMemberListRecyclerViewAdapter extends RecyclerView.Adapter<MyShowMemberListRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyOrganizationMemberRVA";
    private StudentUnionViewModel mViewModel;
    private List<User> memberList = new ArrayList<>();

    public MyShowMemberListRecyclerViewAdapter(StudentUnionViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void setMemberList(List<User> memberList) {
        this.memberList = memberList;
        for (int i = 0; i < memberList.size(); i++) {
            Log.i(TAG, "setActivitiesList: " + memberList.toString());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_organization_member_cell, parent,false);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder: ");
            User user = memberList.get(position);
            holder.tv_icon.setText(user.getName().substring(0,1));
            holder.tv_name.setText(user.getName());
            holder.tv_info.setText(mViewModel.getMemberInfo(user.getAccount()));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_icon,tv_name,tv_info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_icon = itemView.findViewById(R.id.tv_member_enroll_name_icon);
            tv_name = itemView.findViewById(R.id.tv_member_enroll_name);
            tv_info = itemView.findViewById(R.id.tv_member_enroll_info);
        }
    }
}
