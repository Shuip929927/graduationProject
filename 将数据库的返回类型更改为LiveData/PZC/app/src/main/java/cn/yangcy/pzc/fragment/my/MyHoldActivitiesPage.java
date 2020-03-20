 package cn.yangcy.pzc.fragment.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyActivitiesRecyclerViewAdapter;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

 public class MyHoldActivitiesPage extends AppCompatActivity {

     private MyActivitiesRecyclerViewAdapter myActivitiesRecyclerViewAdapter;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.my_recycler_view_page);
         Intent intent = getIntent();
         User user = (User) intent.getSerializableExtra("user");
         MyPageViewModel mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
         RecyclerView mRecyclerView = findViewById(R.id.my_recyclerView);
         myActivitiesRecyclerViewAdapter = new MyActivitiesRecyclerViewAdapter(mViewModel);
         mRecyclerView.setAdapter(myActivitiesRecyclerViewAdapter);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

     }
}
