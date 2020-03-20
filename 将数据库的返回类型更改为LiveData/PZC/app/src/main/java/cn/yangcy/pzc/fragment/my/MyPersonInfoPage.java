package cn.yangcy.pzc.fragment.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
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
import java.util.Arrays;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.MyPersonInfoPageBinding;
import cn.yangcy.pzc.model.department.Department;
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
    private LiveData<List<String>> departmentList;
    private List<String> depListString;
    private int departmentId;
    private String departmentName;
    private ArrayAdapter<String> mDepartmentAdapter;
    private List<String> majorList;
    private int newMajorPosition;//major new position
    private String major;
    private int oldMajorPosition = 0;
    private ArrayAdapter<String> mMajorAdapter;
    private ArrayAdapter<String> mMajorAdapter2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.my_person_info_page);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);

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
                break;

            case R.id.my_person_info_save:
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
