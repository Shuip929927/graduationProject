package cn.yangcy.pzc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.view.LoginActivity;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private HomeViewModel homeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

                //TODO:测试MutableLiveData 转换成LiveData 输出 成功！
//                homeViewModel.toastUser().observe(MainActivity.this, new Observer<User>() {
//                    @Override
//                    public void onChanged(User user) {
//                        Log.i(TAG, "onChanged: "+user.toString());
//                    }
//                });

            }
        });
    }
}
