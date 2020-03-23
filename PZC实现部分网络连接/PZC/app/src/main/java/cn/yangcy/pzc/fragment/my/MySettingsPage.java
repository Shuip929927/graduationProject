package cn.yangcy.pzc.fragment.my;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.MD5Util;
import cn.yangcy.pzc.view.HomeActivity;
import cn.yangcy.pzc.view.LoginActivity;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MySettingsPage extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private MyPageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_settings_page);
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("user");
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        CardView cardUpdatePassword = findViewById(R.id.card_update_password);
        final CardView cardExit = findViewById(R.id.card_exit);

        cardUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater)MySettingsPage.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dialogview_cell, null);
                builder = new AlertDialog.Builder(MySettingsPage.this).setTitle(R.string.tv_settings_update_password)
                        .setView(view)
                        .setPositiveButton(R.string.info_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText oldPassword = view.findViewById(R.id.et_old_password);
                                EditText newPassword = view.findViewById(R.id.et_new_password);
                                EditText checkNewPassword = view.findViewById(R.id.et_check_new_password);
                                if (oldPassword.getText().toString().isEmpty()
                                        || newPassword.getText().toString().isEmpty()
                                        || checkNewPassword.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), R.string.err_input_cannot_empty, Toast.LENGTH_SHORT).show();
                                } else if (!user.getPassword().equals(MD5Util.digest(oldPassword.getText().toString()))) {
                                    Toast.makeText(getApplicationContext(), R.string.err_password, Toast.LENGTH_SHORT).show();
                                } else if (!newPassword.getText().toString().equals(checkNewPassword.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), R.string.err_password_not_equals_check, Toast.LENGTH_SHORT).show();
                                } else {
                                    //TODO :未完成修改
                                    User u = new User(user.getAccount(), MD5Util.digest(newPassword.getText().toString()),
                                            user.getName(),user.getGender(),user.getPhoneNumber(),user.getDepartmentId(),
                                            user.getMajorId(),user.getClasses(),user.getPower());
                                    mViewModel.updateUser(u);
                                    Toast.makeText(getApplicationContext(), R.string.info_update_success, Toast.LENGTH_SHORT).show();
                                    doExist();
                                }
                            }


                        })
                        .setNegativeButton(R.string.info_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doExist();
            }
        });
    }
    private void doExist(){
        startActivity(new Intent(MySettingsPage.this, LoginActivity.class));
        finish();
        HomeActivity.INSTANCE.finish();
    }}
