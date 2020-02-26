package cn.yangcy.pzc.fragment.stundentunoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesDetailPage extends AppCompatActivity {

    private static final String TAG = "SU_ActivitiesDetailPage";
    private StudentUnionViewModel mViewModel;
    private StudentUnionActivitiesDetialPageBinding binding;
    private int activitiesId;
    private LiveData<Activities> activitiesLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_activities_detial_page);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        activitiesId = intent.getIntExtra("activities_id", -1);
        activitiesLiveData = mViewModel.getActivitiesLiveData(activitiesId);


        activitiesLiveData.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<Activities>() {
            LiveData<ActivitiesEnroll> activitiesEnroll = mViewModel.getActivitiesEnroll();

            @Override
            public void onChanged(Activities activities) {
                binding.tvActivitiesDetailTitle.setText(activities.getName());
                binding.tvActivitiesDetailOrganization.setText(activities.getOrganizationName());
                binding.tvActivitiesDetailCreateTime.setText(activities.getCreateOn());
                binding.tvActivitiesDetailStartTime.setText(activities.getStartTime());
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
                    activitiesEnroll.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            final ActivitiesEnroll ae = activitiesEnroll;
//                            if (activitiesEnroll.getState() == 0) {
//                                Log.i(TAG, "onChanged: ??????0");
//                                binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
//                                binding.btEnroll.setText(R.string.info_activities_enroll);
//                                binding.btEnroll.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        ae.setState(1);
//                                        mViewModel.updateActivitiesEnroll(ae);
//                                        binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
//                                        binding.btEnroll.setText(R.string.info_activities_enrolled);
//                                        Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 0);
//                                    }
//                                });
//                            } else if (activitiesEnroll.getState() == 1) {
//                                Log.i(TAG, "onChanged: ??????1");
//                                binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
//                                binding.btEnroll.setText(R.string.info_activities_enrolled);
//                                binding.btEnroll.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        ae.setState(0);
//                                        mViewModel.updateActivitiesEnroll(ae);
//                                        binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
//                                        binding.btEnroll.setText(R.string.info_activities_enroll);
//                                        Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 1);
//                                    }
//                                });
//                            } else if(activitiesEnroll.getState() == 2){
//                                Log.i(TAG, "onChanged: ??????2");
//                                binding.btEnroll.setBackgroundResource(R.drawable.btn_state_enroll_success);
//                                binding.btEnroll.setText(R.string.info_activities_enrolled_success);
//                                binding.btEnroll.setClickable(false);
//                            } else {
//                                binding.btEnroll.setVisibility(View.GONE);
//                            }
                            switch (ae.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: ??????0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setState(1);
                                            mViewModel.updateActivitiesEnroll(ae);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                            binding.btEnroll.setText(R.string.info_activities_enrolled);
                                            Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 0);
                                        }
                                    });
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: ??????1");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setState(0);
                                            mViewModel.updateActivitiesEnroll(ae);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                            binding.btEnroll.setText(R.string.info_activities_enroll);
                                            Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 1);
                                        }
                                    });
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: ??????2");
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
                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                    binding.btEnroll.setClickable(false);

                    activitiesEnroll.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
//                            if (activitiesEnroll.getState() == 0) {
//                                Log.i(TAG, "onChanged: end0");
//                                binding.btEnroll.setText(R.string.info_activities_state_end);
//                            } else if (activitiesEnroll.getState() == 1) {
//                                Log.i(TAG, "onChanged: end1");
//                                binding.btEnroll.setText(R.string.info_activities_state_end);
//                            } else if (activitiesEnroll.getState() == 2) {
//                                Log.i(TAG, "onChanged: end2");
//                                binding.btEnroll.setText(R.string.info_activities_enrolled_success);
//                            } else {
//                                binding.btEnroll.setVisibility(View.GONE);
//                            }
                            switch (activitiesEnroll.getState()) {
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
                    activitiesEnroll.observe(AtyStudentUnionActivitiesDetailPage.this, new Observer<ActivitiesEnroll>() {
                        @Override
                        public void onChanged(ActivitiesEnroll activitiesEnroll) {
                            final ActivitiesEnroll ae = activitiesEnroll;
                            switch (ae.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: ??????0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setState(1);
                                            mViewModel.updateActivitiesEnroll(ae);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                            binding.btEnroll.setText(R.string.info_activities_enrolled);
                                            Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 0);
                                        }
                                    });
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: ??????1");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ae.setState(0);
                                            mViewModel.updateActivitiesEnroll(ae);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                            binding.btEnroll.setText(R.string.info_activities_enroll);
                                            Log.i(TAG, "onClick: " + mViewModel.getUserAccount() + " " + 1);
                                        }
                                    });
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: ??????2");
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
    }
}
