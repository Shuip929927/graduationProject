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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.RegisterPage2FragmentBinding;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.MD5Util;
import cn.yangcy.pzc.view.HomeActivity;
import cn.yangcy.pzc.viewmodel.RegisterViewModel;

public class RegisterPage2 extends Fragment {

    private static final String TAG = "RegisterPage2";
    private RegisterViewModel mViewModel;
    private RegisterPage2FragmentBinding binding;

    private LiveData<List<Department>> departmentListLiveData;
    private List<String> departmentList= new ArrayList<>();
    private MutableLiveData<Integer> departmentId;
    private ArrayAdapter<String> mDepartmentAdapter;

    private LiveData<List<Major>> majorList;
    private List<String> majorNameList = new ArrayList<>();
    private MutableLiveData<Integer> majorId;
    private ArrayAdapter<String> mMajorAdapter;

    public static RegisterPage2 newInstance() {
        return new RegisterPage2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(RegisterViewModel.class);
        binding = DataBindingUtil.inflate(inflater,
                R.layout.register_page2_fragment, container, false);
        binding.setMRegisterViewModel(mViewModel);
        binding.setLifecycleOwner(getActivity());
        departmentListLiveData = mViewModel.getDepartmentList();
        departmentId = mViewModel.getDepartmentId();
        majorId = mViewModel.getMajorId();

        departmentListLiveData.observe(getActivity(), new Observer<List<Department>>() {
            @Override
            public void onChanged(final List<Department> strings) {
                int departmentSelectPosition = 0;
                for (int i = 0; i < strings.size(); i++) {
                    if(strings.get(i).getId() == departmentId.getValue()){
                        departmentSelectPosition = i;
                    }
                    departmentList.add(strings.get(i).getDepartment());
                }
                mDepartmentAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                        R.layout.support_simple_spinner_dropdown_item, departmentList);
                mDepartmentAdapter.notifyDataSetChanged();
                binding.spDepartment.setAdapter(mDepartmentAdapter);
                binding.spDepartment.setSelection(departmentSelectPosition);
                binding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MutableLiveData<Integer> departmentId = mViewModel.getDepartmentId();
                        Log.i(TAG, "onItemSelected: departmentList.get(position)" + strings.get(position));
                        Log.i(TAG, "onItemSelected: department.setValue" + departmentId.getValue());
                        majorList = mViewModel.getMajorList(position + 1);
                        mViewModel.setDepartmentId(departmentId);

                        majorList.observe(getActivity(), new Observer<List<Major>>() {
                            @Override
                            public void onChanged(final List<Major> majors) {
                                int majorSelectPosition = 0;
                                majorNameList.clear();
                                for (int i = 0; i < majors.size(); i++) {
                                    if(majors.get(i).getId() == majorId.getValue()){
                                        majorSelectPosition = i;
                                    }
                                    majorNameList.add(majors.get(i).getMajorName());
                                }
                                mMajorAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                                        R.layout.support_simple_spinner_dropdown_item, majorNameList);
                                mMajorAdapter.notifyDataSetChanged();
                                binding.spMajor.setAdapter(mMajorAdapter);
                                binding.spMajor.setSelection(majorSelectPosition);
                                binding.spMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        MutableLiveData<Integer> major = mViewModel.getMajorId();
                                        for (int i = 0; i < majors.size(); i++) {
                                            if (majors.get(i).getMajorName().equals(majorNameList.get(position))) {
                                                major.setValue(majors.get(i).getId());
                                            }
                                        }
                                        mViewModel.setMajorId(major);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.btBackToRegisterPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "回退界面到Register1 ");
                NavController mNavController = Navigation.findNavController(v);
                mNavController.navigate(R.id.action_registerPage2_to_registerPage1);
            }
        });


        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getName().getValue().isEmpty()
                        || mViewModel.getClasses().getValue().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.err_input_cannot_empty, Toast.LENGTH_SHORT).show();
                } else{
                    mViewModel.registerUser(new User(Integer.parseInt(mViewModel.getAccount().getValue()),
                            MD5Util.digest(mViewModel.getPassword().getValue()),mViewModel.getName().getValue(),
                            mViewModel.getGender().getValue(),MD5Util.digest(mViewModel.getPhone().getValue()),
                            mViewModel.getDepartmentId().getValue(),mViewModel.getMajorId().getValue(),
                            mViewModel.getClasses().getValue()));
                    Toast.makeText(getActivity(), R.string.info_register_success,Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(getContext(), HomeActivity.class);
                    startActivity(login);
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });
    }

}
