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
import cn.yangcy.pzc.fragment.stundentunoin.AtyStudentUnionOrganizationDetailPage;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyEnrollOrganizationRecyclerViewAdapter extends RecyclerView.Adapter<MyEnrollOrganizationRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyEnrollOrganizationRec";
    private MyPageViewModel mViewModel;
    private List<Organization> organizationList = new ArrayList<>();

    public MyEnrollOrganizationRecyclerViewAdapter(MyPageViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }
    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
        for (int i = 0; i < organizationList.size(); i++) {
            Log.i(TAG, "setOrganizationList: " + organizationList.toString());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_organization_cell, parent, false);

        return new MyEnrollOrganizationRecyclerViewAdapter.MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Organization organization = organizationList.get(position);
        holder.tv_organization_name.setText(organization.getOrganization());
        holder.tv_organization_personInCharge.setText(organization.getPersonInCharge());
        holder.tv_organization_personInChargeName.setText(String.valueOf(organization.getNumberOfPeople()));
        int state = organization.getIsEnroll();
        if (state == 0) {
            holder.bt_organizationIsEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
            holder.bt_organizationIsEnroll.setText(R.string.info_activities_state_ongoing);
        } else if (state == 1) {
            holder.bt_organizationIsEnroll.setBackgroundResource(R.drawable.btn_state_end);
            holder.bt_organizationIsEnroll.setText(R.string.info_organization_state_end);
        } else {
            holder.bt_organizationIsEnroll.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(holder.itemView.getContext(), AtyStudentUnionOrganizationDetailPage.class);
                detail.putExtra("organization_id", organization.getId());
                holder.itemView.getContext().startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return organizationList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_organization_name, tv_organization_personInCharge,
                tv_organization_personInChargeName;

        private Button bt_organizationIsEnroll;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_organization_name = itemView.findViewById(R.id.tv_student_union_organization_name);
            tv_organization_personInCharge = itemView.findViewById(R.id.tv_student_union_organization_person_in_charge);
            tv_organization_personInChargeName = itemView.findViewById(R.id.tv_student_union_organization_personInCharge_name);
            bt_organizationIsEnroll = itemView.findViewById(R.id.bt_student_union_organization_isEnroll);
        }
    }
}
