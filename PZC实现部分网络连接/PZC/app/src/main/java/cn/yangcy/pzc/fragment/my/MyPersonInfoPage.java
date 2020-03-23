package cn.yangcy.pzc.fragment.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.MyPersonInfoPageBinding;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyPersonInfoPage extends AppCompatActivity {

    public static MyPersonInfoPage INSTANCE;
    private static final String TAG = "MyPersonInfoPage";
    private MyPersonInfoPageBinding binding;
    private MyPageViewModel mViewModel;
    private AlertDialog.Builder builder;
    private android.app.AlertDialog.Builder v;
    private int clickId = R.id.my_person_info_cancel;
    private User user;
    //TODO 修改
//    private List<String> departmentList;
    private LiveData<List<Department>> departmentListLiveData;
    private List<String> departmentList= new ArrayList<>();
    private int departmentId;
    private int departmentIdNew;
    private String departmentName;
    private ArrayAdapter<String> mDepartmentAdapter;
    private LiveData<List<Major>> majorListLiveData;
    private List<String> majorNameList = new ArrayList<>();
    private int majorId;
    private int majorIdNew;
    private String major;
    private ArrayAdapter<String> mMajorAdapter;
    private ArrayAdapter<String> mMajorAdapter2;
    private int departmentSelectPosition;
    private int departmentSelectPositionNew;
    private int majorSelectPosition;
    private int majorSelectPositionNew;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.my_person_info_page);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        departmentListLiveData = mViewModel.getDepartmentList();
        departmentId = user.getDepartmentId();
        majorId = user.getMajorId();

        binding.etPersonInfoName.setText(user.getName());

        departmentListLiveData.observe(INSTANCE, new Observer<List<Department>>() {
            @Override
            public void onChanged(final List<Department> strings) {
                departmentSelectPosition = 0;
                for (int i = 0; i < strings.size(); i++) {
                    if(strings.get(i).getId() == departmentId){
                        departmentSelectPosition = i;
                    }
                    departmentList.add(strings.get(i).getDepartment());
                }
                mDepartmentAdapter = new ArrayAdapter<>(INSTANCE.getBaseContext(),
                        R.layout.support_simple_spinner_dropdown_item, departmentList);
                mDepartmentAdapter.notifyDataSetChanged();
                binding.spDepartment.setAdapter(mDepartmentAdapter);
                binding.spDepartment.setSelection(departmentSelectPosition);

                binding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG, "默认的departmentId" + departmentId);
                        majorListLiveData = mViewModel.getMajorList(position + 1);
                        for (int i = 0; i < strings.size(); i++) {
                            if(departmentList.get(position).equals(strings.get(i).getDepartment())){
                                departmentIdNew = strings.get(i).getId();
                                Log.i(TAG, "选择的系" + strings.get(i).getDepartment());
                                Log.i(TAG, "选择的系ID" + strings.get(i).getId());
                                departmentSelectPositionNew = position;
                                Log.i(TAG, "选择的系new Position" + strings.get(i).getId());
                            }
                        }

                        majorListLiveData.observe(INSTANCE, new Observer<List<Major>>() {
                            @Override
                            public void onChanged(final List<Major> majors) {
                                majorSelectPosition = 0;
                                majorNameList.clear();
                                for (int i = 0; i < majors.size(); i++) {
                                    if(majors.get(i).getId() == majorId){
                                        majorSelectPosition = i;
                                    }
                                    majorNameList.add(majors.get(i).getMajorName());
                                }
                                mMajorAdapter = new ArrayAdapter<String>(getBaseContext(),
                                        R.layout.support_simple_spinner_dropdown_item, majorNameList);
                                mMajorAdapter.notifyDataSetChanged();
                                binding.spMajor.setAdapter(mMajorAdapter);
                                binding.spMajor.setSelection(majorSelectPosition);
                                binding.spMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        for (int i = 0; i < majors.size(); i++) {
                                            if (majors.get(i).getMajorName().equals(majorNameList.get(position))) {
                                                majorIdNew = majors.get(i).getId();
                                                Log.i(TAG, "选择的专业" + majors.get(i).getMajorName());
                                                Log.i(TAG, "选择的专业ID" + majors.get(i).getId());
                                                majorSelectPositionNew = position;
                                                Log.i(TAG, "选择的专业new Position" + strings.get(i).getId());
                                            }
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        binding.etPersonInfoClasses.setText(user.getClasses());

        binding.tvPersonInfoPhone.setVisibility(View.GONE);
        binding.btUpdataPhoneNum.setVisibility(View.GONE);
        binding.etPersonInfoName.setEnabled(false);
        changEnable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mene_my_person_info, menu);
        switch (clickId) {
            case R.id.my_person_info_save:
            case R.id.my_person_info_cancel:
                menu.findItem(R.id.my_person_info_edit).setVisible(true);
                menu.findItem(R.id.my_person_info_save).setVisible(false);
                menu.findItem(R.id.my_person_info_cancel).setVisible(false);
                break;
            default:
                menu.findItem(R.id.my_person_info_edit).setVisible(false);
                menu.findItem(R.id.my_person_info_save).setVisible(true);
                menu.findItem(R.id.my_person_info_cancel).setVisible(true);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.my_person_info_edit:
                showMenu(R.id.my_person_info_edit);
                changEnable(true);
                break;
            case R.id.my_person_info_cancel:
                binding.spDepartment.setSelection(departmentSelectPosition);
                Log.i(TAG, "onDepartmentSelected: "+ user.getDepartmentId());
                binding.spMajor.setSelection(majorSelectPosition);
                binding.etPersonInfoClasses.setText(user.getClasses());
                showMenu(R.id.my_person_info_cancel);
                changEnable(false);
                break;

            case R.id.my_person_info_save:
                User u = new User(user.getAccount(), user.getPassword(), user.getName(), user.getGender(),user.getPhoneNumber(), user.getDepartmentId(), user.getMajorId(), user.getClasses(), user.getPower());
                u.setDepartmentId(departmentIdNew);
                Log.i(TAG, "save_newDepartmentId: " +departmentIdNew);
                Log.i(TAG, "save_newMajor: " +majorIdNew);
                u.setMajorId(majorIdNew);
                u.setClasses(binding.etPersonInfoClasses.getText().toString());
                mViewModel.updateUser(u);
                binding.spDepartment.setSelection(departmentSelectPositionNew);
                Log.i(TAG, "onOptionsItemSelected: "+ departmentSelectPositionNew);
                binding.spMajor.setSelection(majorSelectPositionNew);
                binding.etPersonInfoClasses.setText(u.getClasses());
                changEnable(false);
                showMenu(R.id.my_person_info_save);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changEnable(boolean enable) {
        binding.spDepartment.setEnabled(enable);
        binding.spMajor.setEnabled(enable);
        binding.etPersonInfoClasses.setEnabled(enable);
    }

    private void showMenu(int id) {
        clickId = id;
        supportInvalidateOptionsMenu();
    }
}
