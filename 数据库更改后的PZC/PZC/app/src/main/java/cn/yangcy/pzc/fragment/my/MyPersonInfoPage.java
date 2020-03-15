package cn.yangcy.pzc.fragment.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import java.util.Arrays;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.MyPersonInfoPageBinding;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyPersonInfoPage extends AppCompatActivity {

    private static final String TAG = "MyPersonInfoPage";
    private MyPersonInfoPageBinding binding;
    private MyPageViewModel mViewModel;
    private AlertDialog.Builder builder;
    private android.app.AlertDialog.Builder v;
    private int clickId = R.id.my_person_info_cancel;
    private User user;
    private List<String> departmentList;
    private int departmentId;
    private ArrayAdapter<String> mDepartmentAdapter;
    private List<String> majorList;
    private int newMajorPosition;//major new position
    private String major;
    private int oldMajorPosition = 0;
    private ArrayAdapter<String> mMajorAdapter;
    private ArrayAdapter<String> mMajorAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_person_info_page);
        binding = DataBindingUtil.setContentView(this, R.layout.my_person_info_page);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        departmentId = user.getDepartmentId();
        departmentList = mViewModel.getDepartmentList();

//        majorId = user.getMajorId();
        majorList = mViewModel.getMajorList(departmentId);
        major = mViewModel.getMajorNameById(user.getMajorId());

        if (departmentList == null) {
            Log.i(TAG, "onCreateView: departmentList is NULL");
            departmentList = new ArrayList<>();
            String[] dList = new String[]{"信息工程系", "计算机工程系", "电气工程系", "化学工程系", "土木工程系"
                    , "建筑系", "机械工程系", "材料工程系", "环境资源工程系", "食品与生物工程系", "传播与艺术系", "经济管理系", "人文艺术系", "外国语系"};
            departmentList.addAll(Arrays.asList(dList));
        }

        mDepartmentAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, departmentList);

        binding.spDepartment.setAdapter(mDepartmentAdapter);

        mMajorAdapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.support_simple_spinner_dropdown_item, majorList);
        binding.spMajor.setAdapter(mMajorAdapter);

        binding.etPersonInfoName.setText(user.getName());

        binding.spDepartment.setSelection(departmentId-1);

        binding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "departmentList.get(position)" + departmentList.get(position));
                Log.i(TAG, "DepartmentPosition" + position);
                majorList.clear();
                majorList = mViewModel.getMajorList(position+1);
                boolean flag = false;
                for (int i = 0; i < majorList.size(); i++) {
                    if(major.equals(majorList.get(i))){
                        oldMajorPosition = i;
                        newMajorPosition = i;
                        Log.i(TAG, "MajorPosition: "+ oldMajorPosition + major);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    oldMajorPosition = 0;
                }
                mMajorAdapter2 = new ArrayAdapter<String>(getBaseContext(),
                        R.layout.support_simple_spinner_dropdown_item, majorList);
                departmentId = position+1;
                binding.spMajor.setAdapter(mMajorAdapter2);
                binding.spMajor.setSelection(oldMajorPosition);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onDepartmentNothingSelected: " +user.getDepartmentId());
//                parent.setSelection(user.getDepartmentId()-1);
            }
        });

        binding.spMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newMajorPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
//TODO :未完成修改
            case R.id.my_person_info_cancel:
//                binding.etPersonInfoDepartment.setText(user.getDepartment());
                binding.spDepartment.setSelection(user.getDepartmentId()-1);
//                Log.i(TAG, "onOptionsItemSelected: " + user.getDepartment());
                Log.i(TAG, "onDepartmentSelected: "+ user.getDepartmentId());
//                binding.etPersonInfoMajor.setText(user.getMajor());
                binding.spMajor.setSelection(oldMajorPosition);
                binding.etPersonInfoClasses.setText(user.getClasses());
                showMenu(R.id.my_person_info_cancel);
                changEnable(false);
                break;

            case R.id.my_person_info_save:
//                User u = new User(user.getAccount(), user.getPassword(), user.getName(), user.getPhoneNumber(), user.getDepartment(), user.getMajor(), user.getClasses(), user.getPower());
                User u = new User(user.getAccount(), user.getPassword(), user.getName(), user.getPhoneNumber(), user.getDepartmentId(), user.getMajorId(), user.getClasses(), user.getPower());
                u.setDepartmentId(departmentId);
                Log.i(TAG, "save_newMajor: "+majorList.get(newMajorPosition));
                Log.i(TAG, "save_newMajorId: "+mViewModel.getMajorIdByName(majorList.get(newMajorPosition)));
                u.setMajor(mViewModel.getMajorIdByName(majorList.get(newMajorPosition)));
                u.setClasses(binding.etPersonInfoClasses.getText().toString());
                if (mViewModel.updateUser(u)) {
//                    binding.etPersonInfoDepartment.setText(u.getDepartment());
                    binding.spDepartment.setSelection(u.getDepartmentId()-1);
//                    binding.etPersonInfoMajor.setText(u.getMajor());
                    Log.i(TAG, "onOptionsItemSelected: "+ newMajorPosition);
                    binding.spMajor.setSelection(newMajorPosition);
                    binding.etPersonInfoClasses.setText(u.getClasses());
                    changEnable(false);
                    showMenu(R.id.my_person_info_save);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changEnable(boolean enable) {
//        binding.etPersonInfoName.setEnabled(enable);
        binding.spDepartment.setEnabled(enable);
        binding.spMajor.setEnabled(enable);
        binding.etPersonInfoClasses.setEnabled(enable);
    }

    private void showMenu(int id) {
        clickId = id;
        supportInvalidateOptionsMenu();
    }
}
