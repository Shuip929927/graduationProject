package cn.yangcy.pzc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.yangcy.pzc.R;

public class RegisterActivity extends AppCompatActivity {

    private RegisterActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        INSTANCE = this;
        getSupportActionBar().hide();
    }
}
