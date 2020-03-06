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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_person_info_page);
        binding = DataBindingUtil.setContentView(this, R.layout.my_person_info_page);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        binding.etPersonInfoName.setText(user.getName());
        binding.etPersonInfoDepartment.setText(user.getDepartment());
        binding.etPersonInfoMajor.setText(user.getMajor());
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
                binding.etPersonInfoDepartment.setText(user.getDepartment());
                Log.i(TAG, "onOptionsItemSelected: "+ user.getDepartment());
                binding.etPersonInfoMajor.setText(user.getMajor());
                binding.etPersonInfoClasses.setText(user.getClasses());
                showMenu(R.id.my_person_info_cancel);
                changEnable(false);
                break;

            case R.id.my_person_info_save:
                User u = new User(user.getAccount(),user.getPassword(),user.getName(),user.getPhoneNumber(),user.getDepartment(),user.getMajor(),user.getClasses(),user.getPower());
                u.setDepartment(binding.etPersonInfoDepartment.getText().toString().trim());
                u.setMajor(binding.etPersonInfoMajor.getText().toString().trim());
                u.setClasses(binding.etPersonInfoClasses.getText().toString());
                if(mViewModel.updateUser(u)){
                    binding.etPersonInfoDepartment.setText(u.getDepartment());
                    binding.etPersonInfoMajor.setText(u.getMajor());
                    binding.etPersonInfoClasses.setText(u.getClasses());
                    changEnable(false);
                    showMenu(R.id.my_person_info_save);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changEnable(boolean enable){
//        binding.etPersonInfoName.setEnabled(enable);
        binding.etPersonInfoDepartment.setEnabled(enable);
        binding.etPersonInfoMajor.setEnabled(enable);
        binding.etPersonInfoClasses.setEnabled(enable);
    }

    private void showMenu(int id) {
        clickId = id;
        supportInvalidateOptionsMenu();
    }
}
