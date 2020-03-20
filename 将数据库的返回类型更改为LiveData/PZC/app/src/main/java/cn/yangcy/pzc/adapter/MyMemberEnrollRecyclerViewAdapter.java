package cn.yangcy.pzc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class MyMemberEnrollRecyclerViewAdapter
        extends RecyclerView.Adapter<MyMemberEnrollRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyOrganizationMemberERA";
    private StudentUnionViewModel mViewModel;
    private String type;
    private List<User> memberEnrollList = new ArrayList<>();

    public MyMemberEnrollRecyclerViewAdapter(StudentUnionViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void setMemberEnrollList(String type,List<User> memberEnrollList) {
        this.type = type;
        this.memberEnrollList = memberEnrollList;
        Log.i(TAG, "setMemberEnrollList ");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_organization_member_enroll_cell, parent,false);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");
        final User user = memberEnrollList.get(position);
        holder.tv_enroll_member_icon.setText(user.getName().substring(0,1));
        holder.tv_enroll_member_name.setText(user.getName());
//        holder.tv_enroll_member_info.setText(mViewModel.getMemberInfo(user.getAccount()));
        holder.bt_enroll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewModel.updateMemberEnrollMessage(type,true,user,2);
//                memberEnrollList.remove(position);
                holder.cardView.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });
        holder.bt_enroll_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewModel.updateMemberEnrollMessage(type,false,user,1);
//                memberEnrollList.remove(position);
                holder.cardView.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberEnrollList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView tv_enroll_member_name,tv_enroll_member_icon,tv_enroll_member_info;
        private Button bt_enroll_refuse,bt_enroll_ok;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.member_enroll_cell_cardView);
            tv_enroll_member_name = itemView.findViewById(R.id.tv_member_enroll_name);
            tv_enroll_member_icon = itemView.findViewById(R.id.tv_member_enroll_name_icon);
            tv_enroll_member_info = itemView.findViewById(R.id.tv_member_enroll_info);
            bt_enroll_refuse = itemView.findViewById(R.id.bt_enroll_refuse);
            bt_enroll_ok = itemView.findViewById(R.id.bt_enroll_ok);
        }
    }
}
