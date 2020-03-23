package cn.yangcy.pzc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.fragment.stundentunoin.ActivitiesMemberListPage;
import cn.yangcy.pzc.fragment.stundentunoin.AtyStudentUnionActivitiesMemberPage;
import cn.yangcy.pzc.fragment.stundentunoin.OrganizationMemberListPage;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class MyShowMemberListRecyclerViewAdapter extends RecyclerView.Adapter<MyShowMemberListRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyShowMemberRVA";
    private StudentUnionViewModel mViewModel;
    private List<User> memberList = new ArrayList<>();
    private String type;
    private LiveData<Department> departmentLiveData;
    private String departmentInfo = "";
    private LiveData<Major> majorLiveData;
    private String memberInfo;

    public MyShowMemberListRecyclerViewAdapter(String type, StudentUnionViewModel mViewModel) {
        this.mViewModel = mViewModel;
        this.type = type;
    }

    public void setMemberList(List<User> memberList) {
        Log.i(TAG, memberList.size() + "  +绑定MemberList " + memberList.toString());
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.student_union_member_cell, parent, false);
        return new MyViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        final User user = memberList.get(position);
        holder.tv_icon.setText(user.getName().substring(0, 1));
        holder.tv_name.setText(user.getName());
        if (user.getGender() == 1) {
            holder.icon_gender.setImageResource(R.drawable.icon_male);
        } else {
            holder.icon_gender.setImageResource(R.drawable.icon_female);
        }
        if ("activities".equals(type)) {
            departmentLiveData = mViewModel.getDepartmentLiveById(user.getDepartmentId());
            departmentLiveData.observe(ActivitiesMemberListPage.INSTANCE,
                    new Observer<Department>() {
                        @Override
                        public void onChanged(final Department department) {
                            majorLiveData = mViewModel.getMajorLiveById(user.getMajorId());
                            majorLiveData.observe(ActivitiesMemberListPage.INSTANCE,
                                    new Observer<Major>() {
                                        @Override
                                        public void onChanged(Major major) {
                                            departmentInfo = department.getDepartment();
                                            memberInfo = departmentInfo + " " + user.getYear() + "  \n" + major.getMajorName() + " " + user.getClasses() + "班";
                                            holder.tv_info.setText(memberInfo);
                                            majorLiveData.removeObserver(this);
                                        }
                                    });
                            departmentLiveData.removeObserver(this);
                        }
                    });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: ORG\n" + user.getName()+"\n"+user.getAccount()+"\n"+user.getDepartmentId());

                }
            });
        } else if("organization".equals(type)){
            departmentLiveData = mViewModel.getDepartmentLiveById(user.getDepartmentId());
            departmentLiveData.observe(OrganizationMemberListPage.INSTANCE,
                    new Observer<Department>() {
                        @Override
                        public void onChanged(final Department department) {
                            majorLiveData = mViewModel.getMajorLiveById(user.getMajorId());
                            majorLiveData.observe(OrganizationMemberListPage.INSTANCE,
                                    new Observer<Major>() {
                                        @Override
                                        public void onChanged(Major major) {
                                            departmentInfo = department.getDepartment();
                                            memberInfo = departmentInfo + " " + user.getYear() + "  \n" + major.getMajorName() + " " + user.getClasses() + "班";
                                            holder.tv_info.setText(memberInfo);
                                        }
                                    });
                        }
                    });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: ORG\n" + user.getName()+"\n"+user.getAccount()+"\n"+user.getDepartmentId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_icon, tv_name, tv_info;
        private ImageView icon_gender;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_icon = itemView.findViewById(R.id.tv_member_enroll_name_icon);
            tv_name = itemView.findViewById(R.id.tv_member_enroll_name);
            tv_info = itemView.findViewById(R.id.tv_member_enroll_info);
            icon_gender = itemView.findViewById(R.id.icon_member_gender);
        }
    }
}
