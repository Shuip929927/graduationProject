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
        LiveData<ActivitiesEnroll> activitiesEnrollLiveOld = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesId(mViewModel.getSpUserAccount(), activitiesId);
        activitiesEnrollLiveOld.observe(INSTANCE, new Observer<ActivitiesEnroll>() {
            @Override
            public void onChanged(ActivitiesEnroll activitiesEnroll) {
                if(activitiesEnroll == null){
                    Log.i(TAG, "在getActivitiesLiveById的观察者中，" +
                            "如果activitiesEnrollLive == null 则创建一个新的 ");
                    mViewModel.addAE(new ActivitiesEnroll(mViewModel.getSpUserAccount(),activitiesId));
                }
            }
        });
        activitiesEnrollLiveNew = mViewModel.getActivitiesEnrollByUserAccountAndActivitiesId(mViewModel.getSpUserAccount(),activitiesId);
        mViewModel.getActivitiesLiveById(activitiesId).observe(this, new Observer<Activities>() {

            @Override
            public void onChanged(Activities activities) {
                binding.tvActivitiesDetailTitle.setText(activities.getName());

                mViewModel.getOrganizationLiveById(activities.getOrganizationId()).observe(INSTANCE, new Observer<Organization>() {
                    @Override
                    public void onChanged(Organization organization) {
                        Log.i(TAG, "在getOrganizationLiveById的观察者设置organizationName ");
                        binding.tvActivitiesDetailOrganization.setText(organization.getOrganization());
                    }
                });
                binding.tvActivitiesDetailCreateTime.setText(activities.getCreateOn());
                binding.tvActivitiesDetailStartTime.setText(activities.getStartTime());
                //根据活动状态设置显示的活动状态文字
                if (activities.getState() == 0) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_ongoing);
                } else if (activities.getState() == 1) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_end);
                } else if (activities.getState() == 2) {
                    binding.tvActivitiesDetailState.setVisibility(View.VISIBLE);
                    binding.tvActivitiesDetailState.setText(R.string.info_activities_state_long);
                } else {
                    binding.tvActivitiesDetailState.setVisibility(View.GONE);
                }
                binding.tvOrganizationDetailDescription.setText(activities.getDescription());

                if (activities.getState() == 0) {
                    Log.i(TAG, "当活动状态为0（报名中）的时候 ");
                    activitiesEnrollLiveNew.observe(INSTANCE, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
//                            Log.i(TAG, "onChanged: "+ activitiesEnroll.toString());
//                            final ActivitiesEnroll ae = activitiesEnroll;
                            ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setState(3);
                            }
                            Log.i(TAG, "当活动报名状态为不同的时候 ");
                            switch (ae.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: 报名0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setState(1);
                                            mViewModel.updateAE(ae);
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
//                                            ae.setState(0);
//                                            mViewModel.updateAE(ae);
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

                } else if (activities.getState() == 1) {
                    Log.i(TAG, "当活动状态为1（结束）的时候 ");
                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                    binding.btEnroll.setClickable(false);
                    Log.i(TAG, "当活动报名状态为不同的时候 ");
                    activitiesEnrollLiveNew.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            ActivitiesEnroll ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setState(3);
                            }
                            switch (ae.getState()) {
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
                } else if (activities.getState() == 2) {
                    Log.i(TAG, "当活动状态为2（长期有效）的时候 ");
                    activitiesEnrollLiveNew.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
//                            final ActivitiesEnroll ae = activitiesEnroll;
                            ActivitiesEnroll ae = activitiesEnroll;
                            if(ae == null){
                                ae = new ActivitiesEnroll();
                                ae.setState(3);
                            }
                            Log.i(TAG, "当活动报名状态为不同的时候 ");
                            switch (ae.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: 报名0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            ae.setState(1);
//                                            mViewModel.updateAE(ae);
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
//                                            ae.setState(0);
//                                            mViewModel.updateAE(ae);
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
                break;
            case 2:
                menu.findItem(R.id.activities_member).setVisible(true);
                menu.findItem(R.id.activities_info_edit).setVisible(false);
                break;
            default:
                menu.findItem(R.id.activities_member).setVisible(true);
                menu.findItem(R.id.activities_info_edit).setVisible(true);
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

//    private void sleep10(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}

