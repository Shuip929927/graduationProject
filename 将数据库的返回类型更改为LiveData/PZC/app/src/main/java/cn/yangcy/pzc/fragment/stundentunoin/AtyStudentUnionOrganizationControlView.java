package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.StudentUnionOrganizationControlViewBinding;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationControlView extends AppCompatActivity {

    private static final String TAG = "AtyStudentUnionOrganiza";
    private StudentUnionOrganizationControlViewBinding binding;
    private AlertDialog.Builder build;
    private StudentUnionViewModel mViewModel;
    private int organizationId;
    private LiveData<Organization> organizationLiveData;
    private Organization org;
    private int isEnroll;
    private boolean isShowMenu = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_organization_control_view);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        organizationId = intent.getIntExtra("organization_id", -1);
        organizationLiveData = mViewModel.getOrganizationLiveById(organizationId);

        organizationLiveData.observe(this, new Observer<Organization>() {
            @Override
            public void onChanged(Organization organization) {
                org = organization;
                binding.etControlOrganization.setText(organization.getOrganization());
                binding.etControlChargePosition.setText(organization.getPersonInCharge());
                binding.etControlPersonInCharge.setText(String.valueOf(organization.getPersonAccount()));
                binding.etControlDescription.setText(organization.getDescription());
                isEnroll = organization.getIsEnroll();
                if(isEnroll == 1){
                    binding.switchOrganizationControl.setChecked(false);
                    binding.tvControlSwitch.setText(R.string.info_organization_state_end);
                } else {
                    binding.switchOrganizationControl.setChecked(true);
                    binding.tvControlSwitch.setText(R.string.info_organization_state_ongoing);
                }
                binding.switchOrganizationControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            isEnroll = 0;
                            binding.tvControlSwitch.setText(R.string.info_organization_state_ongoing);
                        } else {
                            isEnroll = 1;
                            binding.tvControlSwitch.setText(R.string.info_organization_state_end);
                        }
                    }
                });
                if(mViewModel.getSpUserPower()<3 && mViewModel.getSpUserPower()!= org.getPersonAccount()){
                    binding.etControlOrganization.setEnabled(false);
                    binding.etControlChargePosition.setEnabled(false);
                    binding.etControlPersonInCharge.setEnabled(false);
                    binding.switchOrganizationControl.setEnabled(false);
                    binding.etControlDescription.setEnabled(false);
                    hideMenu();

                } else if(mViewModel.getSpUserPower()<3 && mViewModel.getSpUserPower() == org.getPersonAccount()){
                    binding.etControlOrganization.setEnabled(false);
                    binding.etControlChargePosition.setEnabled(false);
                    binding.etControlPersonInCharge.setEnabled(false);
                } else{

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        if(isShowMenu){
            menu.findItem(R.id.organization_save).setVisible(true);
        } else {
            menu.findItem(R.id.organization_save).setVisible(false);
        }
        return true;
    }

    public void hideMenu(){
        isShowMenu = false;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.organization_save:
                Log.i(TAG, "onOptionsItemSelected: ");
                build = new AlertDialog.Builder(this).setTitle(R.string.info_save).setMessage(R.string.info_check_save)
                        .setPositiveButton(R.string.info_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String organizationName = binding.etControlOrganization.getText().toString().trim();
                                String chargePosition = binding.etControlChargePosition.getText().toString().trim();
                                int personInCharge = Integer.parseInt(binding.etControlPersonInCharge.getText().toString().trim());
                                String description = binding.etControlDescription.getText().toString();
                                org.setOrganization(organizationName);
                                org.setPersonInCharge(chargePosition);
                                org.setPersonAccount(personInCharge);
                                org.setIsEnroll(isEnroll);
                                org.setDescription(description);
                                mViewModel.updateOrganization(org);
                                Intent back = new Intent(AtyStudentUnionOrganizationControlView.this,AtyStudentUnionOrganizationDetailPage.class);
                                back.putExtra("organization_id",organizationId);
                                dialog.dismiss();
                                startActivity(back);
                                finish();
                            }
                        }).setNegativeButton(R.string.info_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                build.create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
