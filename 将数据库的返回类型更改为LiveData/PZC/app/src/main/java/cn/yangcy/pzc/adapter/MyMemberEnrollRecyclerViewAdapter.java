package cn.yangcy.pzc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesEnrollPage;
import cn.yangcy.pzc.fragment.stundentunoin.OrganizationEnrollPage;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class MyMemberEnrollRecyclerViewAdapter
        extends RecyclerView.Adapter<MyMemberEnrollRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyOrganizationMemberERA";
    private StudentUnionViewModel mViewModel;
    private String type;
    private String departmentInfo = "";
    private String majorInfo = "";
    private String enrollMemberInfo = "";
    private List<User> memberEnrollList = new ArrayList<>();
    private User user;
    private LiveData<ActivitiesEnroll> aeLive;
    private LiveData<OrganizationEnroll> oeLive;
    private int i = 0;

    public MyMemberEnrollRecyclerViewAdapter(String type, StudentUnionViewModel mViewModel) {
        this.mViewModel = mViewModel;
        this.type = type;
    }

    public void setMemberEnrollList(List<User> memberEnrollList) {

        this.memberEnrollList = memberEnrollList;
        Log.i(TAG, memberEnrollList.size() + "   +setMemberEnrollList " + memberEnrollList.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_member_enroll_cell, parent, false);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");

        user = memberEnrollList.get(position);
        holder.tv_enroll_member_icon.setText(user.getName().substring(0, 1));
        holder.tv_enroll_member_name.setText(user.getName());

        if (mViewModel.getSpUserPower() < 3) {
            holder.bt_enroll_ok.setVisibility(View.GONE);
            holder.bt_enroll_refuse.setVisibility(View.GONE);
        }
        if ("activities".equals(type)) {
            mViewModel.getDepartmentLiveById(user.getDepartmentId()).observe(ActivitiesEnrollPage.INSTANCE,
                    new Observer<Department>() {
                        @Override
                        public void onChanged(Department department) {
                            departmentInfo = department.getDepartment();

                        }
                    });
            mViewModel.getMajorLiveById(user.getMajorId()).observe(ActivitiesEnrollPage.INSTANCE,
                    new Observer<Major>() {
                        @Override
                        public void onChanged(Major major) {
                            majorInfo = major.getMajorName();
                            enrollMemberInfo = departmentInfo + " " + user.getYear() + "  \n" + majorInfo + " " + user.getClasses() + "班";
                            holder.tv_enroll_member_info.setText(enrollMemberInfo);
                        }
                    });
            aeLive = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesId
                    (user.getAccount(), mViewModel.getActivitiesId());

        } else if("organization".equals(type)){
            mViewModel.getDepartmentLiveById(user.getDepartmentId()).observe(OrganizationEnrollPage.INSTANCE,
                    new Observer<Department>() {
                        @Override
                        public void onChanged(Department department) {
                            departmentInfo = department.getDepartment();

                        }
                    });
            mViewModel.getMajorLiveById(user.getMajorId()).observe(OrganizationEnrollPage.INSTANCE,
                    new Observer<Major>() {
                        @Override
                        public void onChanged(Major major) {
                            majorInfo = major.getMajorName();
                            enrollMemberInfo = departmentInfo + " " + user.getYear() + "  \n" + majorInfo + " " + user.getClasses() + "班";
                            holder.tv_enroll_member_info.setText(enrollMemberInfo);
                        }
                    });
            oeLive = mViewModel.getOrganizationEnrollLive(user.getAccount(), mViewModel.getOrganizationId());
        }

        holder.bt_enroll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("activities".equals(type)) {
                    Log.i(TAG, "onClick: ACT ENROLL OK" + i++);
                    aeLive.observe(ActivitiesEnrollPage.INSTANCE, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            Log.i(TAG, "onClick: OK " + activitiesEnroll.toString());
                            activitiesEnroll.setState(2);
                            mViewModel.updateAE(activitiesEnroll);
                            aeLive.removeObserver(this);
                        }
                    });
                } else if ("organization".equals(type)) {
                    Log.i(TAG, "onClick: ORG ENROLL OK" + i++);
                    oeLive.observe(OrganizationEnrollPage.INSTANCE, new Observer<OrganizationEnroll>() {
                        @Override
                        public void onChanged(OrganizationEnroll organizationEnroll) {
                            Log.i(TAG, "onClick: OK " + organizationEnroll.toString());
                            organizationEnroll.setState(2);
                            mViewModel.updateOE(organizationEnroll);
                            oeLive.removeObserver(this);
                        }
                    });

                    final LiveData<Organization> organizationLiveData = mViewModel.getOrganizationLiveById(mViewModel.getOrganizationId());
                    organizationLiveData.observe(
                            OrganizationEnrollPage.INSTANCE, new Observer<Organization>() {
                                @Override
                                public void onChanged(Organization organization) {
                                    int num = organization.getNumberOfPeople();
                                    organization.setNumberOfPeople(num + 1);
                                    Log.i(TAG, "部门人数增加++ " + organization.getNumberOfPeople());
                                    mViewModel.updateOrganization(organization);
                                    organizationLiveData.removeObserver(this);
                                }
                            }
                    );
                }
                memberEnrollList.remove(position);
                holder.cardView.setVisibility(View.GONE);
                notifyItemRemoved(position);
            }
        });
        holder.bt_enroll_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ENROLL REFUSE");
                if ("activities".equals(type)) {
                    aeLive.observe(ActivitiesEnrollPage.INSTANCE, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            Log.i(TAG, "onClick:ACT REFUSE " + activitiesEnroll.toString());
                            activitiesEnroll.setState(0);
                            mViewModel.updateAE(activitiesEnroll);
                            aeLive.removeObserver(this);
                        }
                    });
                } else if ("organization".equals(type)) {
                    oeLive.observe(OrganizationEnrollPage.INSTANCE, new Observer<OrganizationEnroll>() {
                        @Override
                        public void onChanged(OrganizationEnroll organizationEnroll) {
                            Log.i(TAG, "onClick: OK " + organizationEnroll.toString());
                            organizationEnroll.setState(0);
                            mViewModel.updateOE(organizationEnroll);
                            oeLive.removeObserver(this);
                        }
                    });
                }
                memberEnrollList.remove(position);
                holder.cardView.setVisibility(View.GONE);
                notifyItemRemoved(position);
            }
        });

        if (user.getGender() == 1) {
            holder.icon_gender.setImageResource(R.drawable.icon_male);
        } else if (user.getGender() == 2) {
            holder.icon_gender.setImageResource(R.drawable.icon_female);
        }
    }

    @Override
    public int getItemCount() {
        return memberEnrollList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tv_enroll_member_name, tv_enroll_member_icon, tv_enroll_member_info;
        private Button bt_enroll_refuse, bt_enroll_ok;
        private ImageView icon_gender;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.member_enroll_cell_cardView);
            tv_enroll_member_name = itemView.findViewById(R.id.tv_member_enroll_name);
            tv_enroll_member_icon = itemView.findViewById(R.id.tv_member_enroll_name_icon);
            tv_enroll_member_info = itemView.findViewById(R.id.tv_member_enroll_info);
            bt_enroll_refuse = itemView.findViewById(R.id.bt_enroll_refuse);
            bt_enroll_ok = itemView.findViewById(R.id.bt_enroll_ok);
            icon_gender = itemView.findViewById(R.id.icon_enroll_gender);
        }
    }
}
