package cn.yangcy.pzc.fragment.my;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyEnrollOrganizationRecyclerViewAdapter;
import cn.yangcy.pzc.adapter.MyOrganizationRecyclerViewAdapter;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.MyPageViewModel;

public class MyEnrollOrganizationPage extends AppCompatActivity {

    private MyEnrollOrganizationRecyclerViewAdapter myEnrollOrganizationRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recycler_view_page);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        MyPageViewModel mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        RecyclerView mRecyclerView = findViewById(R.id.my_recyclerView);
        myEnrollOrganizationRecyclerViewAdapter = new MyEnrollOrganizationRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(myEnrollOrganizationRecyclerViewAdapter);

        mViewModel.queryMyEnrollOrganization(user.getAccount()).observe(this, new Observer<List<Organization>>() {
            @Override
            public void onChanged(List<Organization> organizations) {
                myEnrollOrganizationRecyclerViewAdapter.setOrganizationList(organizations);
                myEnrollOrganizationRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


    }
}
