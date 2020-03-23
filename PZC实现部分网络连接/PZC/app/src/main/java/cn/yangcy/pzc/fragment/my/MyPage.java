package cn.yangcy.pzc.fragment.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.MyPageFragmentBinding;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class MyPage extends Fragment implements View.OnClickListener {

    private static final String TAG = "MyPage";
    private MyPageFragmentBinding binding;
    private HomeViewModel mViewModel;
    private LiveData<User> user;
    private User u;

    public static MyPage newInstance() {
        return new MyPage();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        user = mViewModel.getUser(mViewModel.getUserAccount());

        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(final User user) {
                binding.tvUserNameIcon.setText(user.getName().substring(0,1));
                binding.tvUserName.setText(user.getName());

                mViewModel.getDepartmentLiveById(user.getDepartmentId()).observe(getActivity(), new Observer<Department>() {
                    @Override
                    public void onChanged(final Department department) {

                        mViewModel.getMajorById(user.getMajorId()).observe(getActivity(), new Observer<Major>() {
                            @Override
                            public void onChanged(Major major) {
                                binding.tvUserInfo.setText(department.getDepartment()+ user.getYear()+
                                        "\n"+major.getMajorName()+" "+user.getClasses()+"班");
                            }
                        });
                    }
                });
                u = user;
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.my_page_fragment,container,false);
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        user = mViewModel.getUser(mViewModel.getUserAccount());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mViewModel.getUserPower()<3){
            binding.cardMyHoldActivities.setVisibility(View.GONE);
        }
        binding.cardPersonInfo.setOnClickListener(this);
        binding.cardMyEnrollActivities.setOnClickListener(this);
        binding.cardMyHoldActivities.setOnClickListener(this);
        binding.cardMyOrganization.setOnClickListener(this);
        binding.cardSettings.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        switch (itemId){
            case R.id.card_person_info:
                Log.i(TAG, "onClick: card_person_info");
                Intent personInfo = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(),MyPersonInfoPage.class);
                personInfo.putExtra("user",u);
                getActivity().startActivity(personInfo);
                break;
            case R.id.card_my_enroll_activities:
                Log.i(TAG, "onClick: card_my_enroll_activities");
                Intent enrollActivities = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), MyEnrollActivitiesPage.class);
                enrollActivities.putExtra("user",u);
                getActivity().startActivity(enrollActivities);
                break;
            case R.id.card_my_hold_activities:
                Log.i(TAG, "onClick: card_my_hold_activities");
                Intent holdActivities = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), MyHoldActivitiesPage.class);
                holdActivities.putExtra("user",u);
                getActivity().startActivity(holdActivities);
                break;

            case R.id.card_my_organization:
                Log.i(TAG, "onClick: card_my_organization");
                Intent enrollOrganization = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), MyEnrollOrganizationPage.class);
                enrollOrganization.putExtra("user",u);
                getActivity().startActivity(enrollOrganization);
                break;
            case R.id.card_settings:
                Log.i(TAG, "onClick: card_settings");
                Intent settingsPage = new Intent(getContext(), MySettingsPage.class);
                settingsPage.putExtra("user",u);
                startActivity(settingsPage);
                break;
            default:
                Log.i(TAG, "onClick: default");
        }
    }
}
