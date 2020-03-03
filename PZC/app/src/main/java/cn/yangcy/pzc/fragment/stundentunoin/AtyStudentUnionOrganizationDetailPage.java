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
import cn.yangcy.pzc.model.user.UserRepository;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationDetailPage extends AppCompatActivity {

    private static final String TAG = "SU_OrgDetailPagePage";
    private StudentUnionViewModel mViewModel;
    private UserRepository userRepository;
    private StudentUnionOrganizationDetialPageBinding binding;
    private int organizationId;
    private LiveData<Organization> organizationLiveData;
    private int mMenuVisibleLevel = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepository = new UserRepository(getApplication());
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_organization_detial_page);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        organizationId = intent.getIntExtra("organization_id", -1);
        organizationLiveData = mViewModel.getOrganizationLiveData(organizationId);

        organizationLiveData.observe(AtyStudentUnionOrganizationDetailPage.this, new Observer<Organization>() {
            LiveData<OrganizationEnroll> organizationEnroll = mViewModel.getOrganizationEnrollLive();

            @Override
            public void onChanged(Organization organization) {
                binding.tvOrganizationName.setText(organization.getOrganization());
                binding.tvOrganizationPersonInCharge.setText(organization.getPersonInCharge());
                String name = userRepository.queryUser(String.valueOf(organization.getPersonAccount())).toPersonInCharge();
                binding.tvOrganizationPersonInChargeName.setText(name);
                binding.tvOrganizationNumberOfPeople.setText(String.valueOf(organization.getNumberOfPeople()));
                if (organization.getIsEnroll() == 0) {
                    binding.tvOrganizationEnrollState.setVisibility(View.VISIBLE);
                    binding.tvOrganizationEnrollState.setText(R.string.info_organization_state_ongoing);
                } else if (organization.getIsEnroll() == 1) {
                    binding.tvOrganizationEnrollState.setVisibility(View.VISIBLE);
                    binding.tvOrganizationEnrollState.setText(R.string.info_organization_state_end);
                } else {
                    binding.tvOrganizationEnrollState.setVisibility(View.GONE);
                }
                binding.tvOrganizationDetailDescription.setText(organization.getDescription());

                if (organization.getIsEnroll() == 0 && mViewModel.getUserPower() < 3
                        && mViewModel.getUserEnrollOrganizationNum() < 3) {
                    /*
                    *   getIsEnroll = 0 部门正在纳新
                    *   getUserPower < 3 该用户不是部门负责人、老师、管理员
                    *   getUserEnrollOrganizationNum < 3 该用户加入部门数量小于3个
                    *
                    *   */
                    organizationEnroll.observe(AtyStudentUnionOrganizationDetailPage.this, new Observer<OrganizationEnroll>() {
                        @Override
                        public void onChanged(OrganizationEnroll organizationEnroll) {
                            final OrganizationEnroll oe = organizationEnroll;

                            switch (oe.getState()) {
                                case 0:
                                    Log.i(TAG, "onChanged: ??????0");
                                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_ongoing_or_long);
                                    binding.btEnroll.setText(R.string.info_activities_enroll);
                                    binding.btEnroll.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            oe.setState(1);
                                            mViewModel.updateOrganizationEnroll(oe);
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
                                            oe.setState(0);
                                            mViewModel.updateOrganizationEnroll(oe);
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
//                } else if (mViewModel.getUserPower() < 3 && mViewModel.getUserEnrollOrganizationNum() > 3) {
//                    binding.btEnroll.setBackgroundResource(R.drawable.btn_state_end);
//                    binding.btEnroll.setText(R.string.info_organization_enroll_full);
//                    binding.btEnroll.setClickable(false);
                } else {
                    binding.btEnroll.setVisibility(View.GONE);
                }
            }
        });


        if (mViewModel.getUserPower() > 1) {
            showMenuLevel3(mViewModel.getUserPower());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_organization, menu);
        switch (mMenuVisibleLevel) {
            case 1:
                menu.findItem(R.id.member).setVisible(false);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            default:
                menu.findItem(R.id.member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.member:
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
