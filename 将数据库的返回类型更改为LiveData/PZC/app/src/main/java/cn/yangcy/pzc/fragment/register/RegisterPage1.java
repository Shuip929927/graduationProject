package cn.yangcy.pzc.fragment.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Objects;
import java.util.Observable;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.RegisterPage1FragmentBinding;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.view.LoginActivity;
import cn.yangcy.pzc.viewmodel.RegisterViewModel;

public class RegisterPage1 extends Fragment {

    private static final String TAG = "RegisterPage1";
    private RegisterViewModel mViewModel;
    private RegisterPage1FragmentBinding binding;
    private LiveData<User> queryUserExist;

    public static RegisterPage1 newInstance() {
        return new RegisterPage1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(RegisterViewModel.class);

        binding = DataBindingUtil.inflate(inflater,
                R.layout.register_page1_fragment,container,false);
        binding.setMRegisterViewModel(mViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.rgGander.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_male){
                    mViewModel.setGender(1);
                } else {
                    mViewModel.setGender(2);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.btToRegisterPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(mViewModel.getAccount().getValue().isEmpty()
                        || mViewModel.getPassword().getValue().isEmpty()
                        ||mViewModel.getCheckPassword().getValue().isEmpty()
                        || mViewModel.getPhone().getValue().isEmpty()){
                    Log.i(TAG, "ERR 输入不能为空");
                    Toast.makeText(getActivity(),R.string.err_input_cannot_empty,Toast.LENGTH_SHORT).show();
                } else{
                    if (!mViewModel.getPassword().getValue().equals(mViewModel.getCheckPassword().getValue())){
                        Log.i(TAG, "ERR 密码与确认密码不一致");
                        Toast.makeText(getActivity(),R.string.err_password_not_equals_check,Toast.LENGTH_SHORT).show();
                    } else if (mViewModel.getPhone().getValue().length() != 11) {
                        Toast.makeText(getActivity(), R.string.err_phone_lenth, Toast.LENGTH_SHORT).show();
                    } else {
                        queryUserExist = mViewModel.queryUserByAccount(Integer.parseInt(mViewModel.getAccount().getValue()));
                        queryUserExist.observe(getActivity(), new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                if(user != null){
                                    Log.i(TAG, "ERR 用户已存在");
                                    Toast.makeText(getActivity(),R.string.err_user_exist,Toast.LENGTH_SHORT).show();
                                }  else{
                                    Log.i(TAG, "跳转界面至Register2");
                                    NavController mNavController = Navigation.findNavController(v);
                                    mNavController.navigate(R.id.action_registerPage1_to_registerPage2);
                                    Log.i(TAG, " queryUserExist 移除观察者");
                                    if(queryUserExist.hasObservers()){
                                        queryUserExist.removeObservers(getActivity());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}
