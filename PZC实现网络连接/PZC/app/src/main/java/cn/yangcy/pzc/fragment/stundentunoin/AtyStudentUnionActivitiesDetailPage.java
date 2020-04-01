package cn.yangcy.pzc.fragment.stundentunoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.StudentUnionActivitiesDetialPageBinding;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.enroll.ActivitiesEnroll;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesDetailPage extends AppCompatActivity {

    private static final String TAG = "SU_ActivitiesDetailPage";
    public static AtyStudentUnionActivitiesDetailPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private StudentUnionActivitiesDetialPageBinding binding;
    private int activitiesId;
    private int mMenuVisibleLevel = 1;
    private LiveData<ActivitiesEnroll> activitiesEnrollLiveNew;
    private ActivitiesEnroll ae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_activities_detial_page);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();

        activitiesId = intent.getIntExtra("activities_id", -1);
        LiveData<ActivitiesEnroll> activitiesEnrollLiveOld;
        if(NetWorkUtil.isConnected(getBaseContext())){
           activitiesEnrollLiveOld = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesIdNet(mViewModel.getSpUserAccount(), activitiesId);
        } else {
            activitiesEnrollLiveOld = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesId(mViewModel.getSpUserAccount(), activitiesId);
        }
        activitiesEnrollLiveOld.observe(INSTANCE, new Observer<ActivitiesEnroll>() {
            @Override
            public void onChanged(ActivitiesEnroll activitiesEnroll) {
                if(activitiesEnroll == null){
                    Log.i(TAG, "在getActivitiesLiveById的观察者中，" +
                            "如果activitiesEnrollLive == null 则创建一个新的 ");
                   if(NetWorkUtil.isConnected(getBaseContext())){
                       mViewModel.addAENet(new ActivitiesEnroll(mViewModel.getSpUserAccount(),activitiesId));
                   } else {
                       mViewModel.addAE(new ActivitiesEnroll(mViewModel.getSpUserAccount(),activitiesId));
                   }
                }
            }
        });
        if(NetWorkUtil.isConnected(getBaseContext())){
            activitiesEnrollLiveNew = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesIdNet(mViewModel.getSpUserAccount(),activitiesId);
        } else {
            activitiesEnrollLiveNew = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesId(mViewModel.getSpUserAccount(),activitiesId);
        }
        LiveData<Activities> actLive;
        if(NetWorkUtil.isConnected(getBaseContext())){
            actLive = mViewModel.getActivitiesLiveByIdNet(activitiesId);
        } else {
            actLive = mViewModel.getActivitiesLiveById(activitiesId);
        }

        actLive.observe(this, new Observer<Activities>() {

            @Override
            public void onChanged(Activities activities) {
                binding.tvActivitiesDetailTitle.setText(activities.getActivity_name());
                LiveData<Organization> orgLive;
                if(NetWorkUtil.isConnected(getBaseContext())){
                    orgLive = mViewModel.getOrganizationLiveByIdNet(activities.getOrganization_id());
                } else {
                    orgLive = mViewModel.getOrganizationLiveById(activities.getOrganization_id());
                }
                orgLive.observe(INSTANCE, new Observer<Organization>() {
                    @Override
                    public void onChanged(Organization organization) {
                        Log.i(TAG, "在getOrganizationLiveById的观察者设置organizationName ");
                        binding.tvActivitiesDetailOrganization.setText(organization.getOrganization_name());
                    }
                });
                binding.tvActivitiesDetailCreateTime.setText(activities.getCreate_time());
                binding.tvActivitiesDetailStartTime.setText(activities.getStart_time());
                //根据活动状态设置显示的活动状态文字
                if (activities.getActivity_state() == 0) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_ongoing);
                } else if (activities.getActivity_state() == 1) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_end);
                } else if (activities.getActivity_state() == 2) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_long);
                } else {
                    binding.tvActivitiesDetailState.setVisibility(View.GONE);
                }
                binding.tvOrganizationDetailDescription.setText(activities.getDescription());

                if (activities.getActivity_state() == 0) {
                    Log.i(TAG, "当活动状态为0（报名中）的时候 ");
                    activitiesEnrollLiveNew.observe(INSTANCE, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setActivity_enroll_state(3);
                            }
                            Log.i(TAG, "当活动报名状态为不同的时候 ");
                            switch (ae.getActivity_enroll_state()) {
                                case 0:
                                    Log.i(TAG, "onChanged: 报名0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setActivity_enroll_state(1);

                                            if(NetWorkUtil.isConnected(getBaseContext())){
                                                mViewModel.updateAENet(ae);
                                            } else {
                                                mViewModel.updateAE(ae);
                                            }

                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                            binding.btEnroll.setText(R.string.info_activities_enrolled);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserAccount() + " " + 0);
                                        }
                                    });
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: 待审核1");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setActivity_enroll_state(0);

                                            if(NetWorkUtil.isConnected(getBaseContext())){
                                                mViewModel.updateAENet(ae);
                                            } else {
                                                mViewModel.updateAE(ae);
                                            }

                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                            binding.btEnroll.setText(R.string.info_activities_enroll);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserAccount() + " " + 1);
                                        }
                                    });
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: 报名成功2");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled_success);
                                    binding.btEnroll.setClickable(false);
                                    break;
                                default:
                                    binding.btEnroll.setVisibility(View.GONE);
                            }
                        }
                    });

                } else if (activities.getActivity_state() == 1) {
                    Log.i(TAG, "当活动状态为1（结束）的时候 ");
                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                    binding.btEnroll.setClickable(false);
                    Log.i(TAG, "当活动报名状态为不同的时候 ");
                    activitiesEnrollLiveNew.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setActivity_enroll_state(3);
                            }
                            switch (ae.getActivity_enroll_state()) {
                                case 0:
                                    Log.i(TAG, "onChanged: end0");
                                    binding.btEnroll.setText(R.string.info_activities_state_end);
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: end1");
                                    binding.btEnroll.setText(R.string.info_activities_state_end);
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: end2");
                                    binding.btEnroll.setText(R.string.info_activities_enrolled_success);
                                    break;
                                default:
                                    binding.btEnroll.setVisibility(View.GONE);
                            }
                        }
                    });
                } else if (activities.getActivity_state() == 2) {
                    Log.i(TAG, "当活动状态为2（长期有效）的时候 ");
                    activitiesEnrollLiveNew.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setActivity_enroll_state(3);
                            }
                            Log.i(TAG, "当活动报名状态为不同的时候 ");
                            switch (ae.getActivity_enroll_state()) {
                                case 0:
                                    Log.i(TAG, "onChanged: 报名0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setActivity_enroll_state(1);

                                            if(NetWorkUtil.isConnected(getBaseContext())){
                                                mViewModel.updateAENet(ae);
                                            } else {
                                                mViewModel.updateAE(ae);
                                            }

                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                            binding.btEnroll.setText(R.string.info_activities_enrolled);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserAccount() + " " + 0);
                                        }
                                    });
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: 待审核1");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setActivity_enroll_state(0);

                                            if(NetWorkUtil.isConnected(getBaseContext())){
                                                mViewModel.updateAENet(ae);
                                            } else {
                                                mViewModel.updateAE(ae);
                                            }

                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                            binding.btEnroll.setText(R.string.info_activities_enroll);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserAccount() + " " + 1);
                                        }
                                    });
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: 报名成功2");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled_success);
                                    binding.btEnroll.setClickable(false);
                                    break;
                                default:
                                    binding.btEnroll.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

        if (mViewModel.getSpUserPower() > 1) {
            Log.i(TAG, "加载用户的权限");
            showMenuLevel3(mViewModel.getSpUserPower());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_activities, menu);
        Log.i(TAG, "不同的权限 显示不同的菜单按钮");
        switch (mMenuVisibleLevel) {
            case 1:
                menu.findItem(R.id.activities_member).setVisible(false);
                menu.findItem(R.id.activities_info_edit).setVisible(false);
                binding.btEnroll.setVisibility(View.GONE);
                break;
            case 2:
                menu.findItem(R.id.activities_member).setVisible(true);
                menu.findItem(R.id.activities_info_edit).setVisible(false);
                binding.btEnroll.setVisibility(View.VISIBLE);
                break;
            default:
                menu.findItem(R.id.activities_member).setVisible(true);
                menu.findItem(R.id.activities_info_edit).setVisible(true);
                binding.btEnroll.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.activities_member:
                intent = new Intent(this, AtyStudentUnionActivitiesMemberPage.class);
                intent.putExtra("activities_id", activitiesId);
                startActivity(intent);
                break;
            case R.id.activities_info_edit:
                intent = new Intent(this, AtyStudentUnionActivitiesControlView.class);
                intent.putExtra("activities_id", activitiesId);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMenuLevel3(int level) {
        mMenuVisibleLevel = level;
        supportInvalidateOptionsMenu();
    }

}

