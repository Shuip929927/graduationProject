package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.StudentUnionActivitesControlViewBinding;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesControlView extends AppCompatActivity {

    private static final String TAG = "SU_ActControlView";
    public static AtyStudentUnionActivitiesControlView INSTANCE;
    private StudentUnionActivitesControlViewBinding binding;
    private StudentUnionViewModel mViewModel;
    private int activitiesId;
    private LiveData<Activities> activitiesLiveData;
    private Activities act;
    private Organization orgOfAct;
    private List<String> organizationList= new ArrayList<>();
    private ArrayAdapter<String> mOrganizationAdapter;
    private boolean isShowMenu = true;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.student_union_activities_control_view);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this,R.layout.student_union_activites_control_view);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        activitiesId = intent.getIntExtra("activities_id", -1);
        activitiesLiveData = mViewModel.getActivitiesLiveById(activitiesId);

        activitiesLiveData.observe(this, new Observer<Activities>() {
            @Override
            public void onChanged(final Activities activities) {
                act = activities;

                binding.etActivitiesTitle.setText(activities.getName());
//                mViewModel.getOrganizationLiveById(activities.getOrganizationId()).observe(INSTANCE, new Observer<Organization>() {
//                    @Override
//                    public void onChanged(Organization organization) {
//                        orgOfAct = organization;
//                        binding.etActivitiesHoldOrganization.setText(organization.getOrganization());
//                    }
//                });
                mViewModel.getAllOrganizationListLive().observe(INSTANCE, new Observer<List<Organization>>() {
                    @Override
                    public void onChanged(final List<Organization> organizations) {
                        int organizationSelectPosition = 0;
                        for (int i = 0; i < organizations.size(); i++) {
                            if(organizations.get(i).getId() == activities.getOrganizationId()){
                                organizationSelectPosition = i;
                            }
                            organizationList.add(organizations.get(i).getOrganization());
                        }
                        mOrganizationAdapter = new ArrayAdapter<String>(getBaseContext(),
                                R.layout.support_simple_spinner_dropdown_item, organizationList);
                        mOrganizationAdapter.notifyDataSetChanged();
                        binding.spOrganization.setAdapter(mOrganizationAdapter);
                        binding.spOrganization.setSelection(organizationSelectPosition);

                        binding.spOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                int organizationId;
                                Log.i(TAG, "onItemSelected: departmentList.get(position)" + organizations.get(position));
                                Log.i(TAG, "onItemSelected: department.setValue" );
                                for (int i = 0; i < organizationList.size(); i++) {
                                    if(organizationList.get(position).equals(organizations.get(i).getOrganization())){
                                        act.setOrganizationId(organizations.get(i).getId());
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

                binding.etActivitiesStartTime.setText(activities.getStartTime());
                binding.etActivitiesDescription.setText(activities.getDescription());
                if(activities.getState() == 0){
                    binding.rbActivitiesStateOngoing.setChecked(true);
                } else if(activities.getState() == 1){
                    binding.rbActivitiesStateEnd.setChecked(true);
                } else if(activities.getState() == 2){
                    binding.rbActivitiesStateLong.setChecked(true);
                }

                binding.rgActivitiesState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_activities_state_ongoing){
                            act.setState(0);
                            Log.i(TAG, "onCheckedChanged: end");
                        } else if(checkedId == R.id.rb_activities_state_end){
                            act.setState(1);
                            Log.i(TAG, "onCheckedChanged: ongiong");
                        } else if(checkedId == R.id.rb_activities_state_long){
                            act.setState(2);
                            Log.i(TAG, "onCheckedChanged: long");
                        }
                    }
                });

                if(mViewModel.getSpUserPower()<4 && orgOfAct.getPersonAccount()!= mViewModel.getSpUserAccount()){
                    Log.i(TAG, "当用户的权限小于4的时候 并且不是这项活动举办部门的负责人的时候（权限为3） ");
                    binding.etActivitiesTitle.setEnabled(false);
                    binding.spOrganization.setEnabled(false);
                    binding.etActivitiesStartTime.setEnabled(false);
                    binding.etActivitiesDescription.setEnabled(false);
                    binding.rbActivitiesStateEnd.setEnabled(false);
                    binding.rbActivitiesStateOngoing.setEnabled(false);
                    binding.rbActivitiesStateLong.setEnabled(false);
                    hideMenu();

                    //之前的考虑是通过用户Account 查询其是否为某个部门的负责人，返回这个部门的Id 与本活动举办部门的id进行对比
//                } else if(mViewModel.getSpUserPower()<4 && mViewModel.searchPersonInChargeOrgId(mViewModel.getUserAccount()) == act.getOrganizationId()){
                } else if(mViewModel.getSpUserPower()<4 && orgOfAct.getPersonAccount() == mViewModel.getSpUserAccount()){
                    Log.i(TAG, "当用户的权限小于4的时候 但却是这项活动举办部门的负责人的时候（权限为3） ");
                    binding.spOrganization.setEnabled(false);
                } else{
                    Log.i(TAG, "当用户的权限大于4的时候 属于有权限管理 ");
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
        Log.i(TAG, "隐藏菜单栏的按钮");
        isShowMenu = false;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.organization_save:
                Log.i(TAG, "onOptionsItemSelected: ");
                builder = new AlertDialog.Builder(this).setTitle(R.string.info_save).setMessage(R.string.info_check_save)
                        .setPositiveButton(R.string.info_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String activitiesTitle = binding.etActivitiesTitle.getText().toString().trim();
//                                String activitiesHoldOrg = binding.etActivitiesHoldOrganization.getText().toString().trim();
                                String activitiesStartTime = binding.etActivitiesStartTime.getText().toString().trim();
                                String description = binding.etActivitiesDescription.getText().toString();
                                act.setName(activitiesTitle);
                                act.setStartTime(activitiesStartTime);
                                act.setDescription(description);
                                mViewModel.updateActivities(act);
                                Intent back = new Intent(AtyStudentUnionActivitiesControlView.this, AtyStudentUnionActivitiesDetailPage.class);
                                back.putExtra("activities_id", activitiesId);
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
                builder.create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
