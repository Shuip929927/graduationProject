package cn.yangcy.pzc.fragment.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.MyPageFragmentBinding;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class MyPage extends Fragment implements View.OnClickListener {

    private static final String TAG = "MyPage";
    private MyPageFragmentBinding binding;
    private HomeViewModel mViewModel;
    private User user;

    public static MyPage newInstance() {
        return new MyPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.my_page_fragment, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.my_page_fragment,container,false);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        user = mViewModel.getUser(mViewModel.getUserAccount());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.tvUserNameIcon.setText(user.getName().substring(0,1));
        binding.tvUserName.setText(user.getName());
        binding.tvUserInfo.setText(user.getUserInfo()+" "+user.getClasses()+"班");
        if(mViewModel.getUserPower()<3){
            binding.cardMyHoldActivities.setVisibility(View.GONE);
        }
        binding.cardMyEnrollActivities.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        switch (itemId){
            case R.id.card_my_enroll_activities:
//                Toast.makeText(getContext(),String.valueOf(mViewModel.getUserPower()),Toast.LENGTH_SHORT).show();
        }
    }
}
