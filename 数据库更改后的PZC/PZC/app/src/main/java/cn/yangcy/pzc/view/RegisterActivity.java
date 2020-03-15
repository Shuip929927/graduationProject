package cn.yangcy.pzc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.yangcy.pzc.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
    }
}
