package cn.yangcy.pzc.fragment.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.RegisterPage2FragmentBinding;
import cn.yangcy.pzc.view.HomeActivity;
import cn.yangcy.pzc.view.LoginActivity;
import cn.yangcy.pzc.view.RegisterActivity;
import cn.yangcy.pzc.viewmodel.RegisterViewModel;

public class RegisterPage2 extends Fragment {

    private static final String TAG = "RegisterPage2";
    private RegisterViewModel mViewModel;
    private RegisterPage2FragmentBinding registerPage2FragmentBinding;
    private List<String> departmentList;

    public static RegisterPage2 newInstance() {
        return new RegisterPage2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
//        mViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).
//                create(RegisterViewModel.class);

        mViewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
//        return inflater.inflate(R.layout.register_page2_fragment, container, false);
        registerPage2FragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.register_page2_fragment, container, false);

        registerPage2FragmentBinding.setMRegisterViewModel(mViewModel);
        registerPage2FragmentBinding.setLifecycleOwner(getActivity());


        departmentList = mViewModel.getDepartmentList();
        if (departmentList == null) {
            Log.i(TAG, "onCreateView: departmentList is NULL");
            departmentList = new ArrayList<>();
            String[] dList = new String[]{"信息工程系", "计算机工程系", "电气工程系", "化学工程系", "土木工程系"
                    , "建筑系", "材料工程系", "环境资源工程系", "食品与生物工程系", "传播与艺术系", "经济管理系", "人文艺术系", "外国语系"};
            for (int i = 0; i < dList.length; i++) {
                departmentList.add(dList[i]);
            }
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                R.layout.support_simple_spinner_dropdown_item, departmentList);

        registerPage2FragmentBinding.spDepartment.setAdapter(mAdapter);

        return registerPage2FragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        registerPage2FragmentBinding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MutableLiveData<String> department = new MutableLiveData<String>();
                department.setValue(departmentList.get(position));
                Log.i(TAG, "onItemSelected: departmentList.get(position)" + departmentList.get(position));
                Log.i(TAG, "onItemSelected: department.setValue" + department.getValue());
                mViewModel.setDepartment(department);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        registerPage2FragmentBinding.btBackToRegisterPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController mNavController = Navigation.findNavController(v);
                mNavController.navigate(R.id.action_registerPage2_to_registerPage1);
            }
        });

        registerPage2FragmentBinding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.doRegister()){
                    Intent login = new Intent(getContext(), HomeActivity.class);
                    startActivity(login);
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    Toast.makeText(getContext(), R.string.fail_register,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
