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
import cn.yangcy.pzc.databinding.StudentUnionOrganizationDetialPageBinding;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationDetailPage extends AppCompatActivity {

    private static final String TAG = "SU_OrgDetailPagePage";
    public static AtyStudentUnionOrganizationDetailPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private StudentUnionOrganizationDetialPageBinding binding;
    private int organizationId;
    private int mMenuVisibleLevel = 1;
    private LiveData<OrganizationEnroll> organizationEnrollNew;
    private OrganizationEnroll oe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_organization_detial_page);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        organizationId = intent.getIntExtra("organization_id", -1);
        LiveData<OrganizationEnroll> organizationEnrollOld = mViewModel.getOrganizationEnrollLive(mViewModel.getSpUserAccount(), organizationId);
        organizationEnrollOld.observe(INSTANCE, new Observer<OrganizationEnroll>() {
            @Override
            public void onChanged(OrganizationEnroll organizationEnroll) {
                if (organizationEnroll == null) {
                    Log.i(TAG, "在getActivitiesLiveById的观察者中，" +
                            "如果organizationEnroll == null 则创建一个新的 ");
                    mViewModel.addOE(new OrganizationEnroll(mViewModel.getSpUserAccount(), organizationId));
                }
            }
        });
        organizationEnrollNew = mViewModel.getOrganizationEnrollLive(mViewModel.getSpUserAccount(),organizationId);
        mViewModel.getOrganizationLiveById(organizationId).observe(INSTANCE, new Observer<Organization>() {
            @Override
            public void onChanged(final Organization organization) {
                Log.i(TAG, "onChanged: "+ organization.toString());
                binding.tvOrganizationName.setText(organization.getOrganization());
                binding.tvOrganizationPersonInCharge.setText(organization.getPersonInCharge());

                mViewModel.getUserLiveByAccount(organization.getPersonAccount()).observe(INSTANCE, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        Log.i(TAG, "设置部门负责人名字 ");
                        binding.tvOrganizationPersonInChargeName.setText(user.getName());
                    }
                });
                if (organization.getIsEnroll() == 0 && mViewModel.getSpUserPower() < 3) {
                    /*
                     *   getIsEnroll = 0 部门正在纳新
                     *   getUserPower < 3 该用户不是部门负责人、老师、管理员
                     */
                    organizationEnrollNew.observe(INSTANCE, new Observer<OrganizationEnroll>() {
                        @Override
                        public void onChanged(OrganizationEnroll organizationEnroll) {
                            oe = organizationEnroll;
                            if(oe == null){
                                oe = new OrganizationEnroll();
                                oe.setState(3);
                            }
                            Log.i(TAG, "OE: "+oe.toString());
                            Log.i(TAG, "当纳新状态为不同的时候" + oe.toString());
                            switch (oe.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: 报名");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            oe.setState(1);
//                                            mViewModel.updateOE(oe);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                            binding.btEnroll.setText(R.string.info_activities_enrolled);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserPower() + " " + 0);
                                        }
                                    });
                                    break;
                                case 1:
                                    Log.i(TAG, "onChanged: 待审核");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            oe.setState(0);
//                                            mViewModel.updateOE(oe);
                                            binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                            binding.btEnroll.setText(R.string.info_activities_enroll);
                                            Log.i(TAG, "onClick: " + mViewModel.getSpUserPower() + " " + 1);
                                        }
                                    });
                                    break;
                                case 2:
                                    Log.i(TAG, "onChanged: 报名成功");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enrolled_success);
                                    binding.btEnroll.setClickable(false);
                                    break;
                                default:
                                    binding.btEnroll.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    binding.btEnroll.setVisibility(View.GONE);
                }

                binding.tvOrganizationNumberOfPeople.setText(String.valueOf(organization.getNumberOfPeople()));
                if (organization.getIsEnroll() == 0) {
                    Log.i(TAG, "部门正在纳新");
                    binding.tvOrganizationEnrollState.setVisibility(View.VISIBLE);
                    binding.tvOrganizationEnrollState.setText(R.string.info_organization_state_ongoing);
                } else if (organization.getIsEnroll() == 1) {
                    Log.i(TAG, "部门纳新结束");
                    binding.tvOrganizationEnrollState.setVisibility(View.VISIBLE);
                    binding.tvOrganizationEnrollState.setText(R.string.info_organization_state_end);
                } else {
                    Log.i(TAG, "部门报名成功");
                    binding.tvOrganizationEnrollState.setVisibility(View.GONE);
                }
                binding.tvOrganizationDetailDescription.setText(organization.getDescription());
            }

        });
        if (mViewModel.getSpUserPower() > 1) {
            showMenuLevel3(mViewModel.getSpUserPower());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_organization, menu);
        switch (mMenuVisibleLevel) {
            case 1:
                menu.findItem(R.id.organization_member).setVisible(false);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.organization_member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            default:
                menu.findItem(R.id.organization_member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.organization_member:
                intent = new Intent(this, AtyStudentUnionOrganizationMemberPage.class);
                intent.putExtra("organization_id", organizationId);
                startActivity(intent);
//                finish();
                break;
            case R.id.organization_info_edit:
                intent = new Intent(this, AtyStudentUnionOrganizationControlView.class);
                intent.putExtra("organization_id", organizationId);
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
