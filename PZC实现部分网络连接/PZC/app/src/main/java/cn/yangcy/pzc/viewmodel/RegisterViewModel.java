package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private static final String TAG = "RegisterViewModel";
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> checkPassword;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> name;
    private MutableLiveData<Integer> gender;
    private MutableLiveData<Integer> departmentId;
    private MutableLiveData<Integer> majorId;
    private MutableLiveData<String> classes;

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        majorRepository = new MajorRepository(application);
        Log.i(TAG, "获取Repository实例 ");
    }

    public LiveData<List<Department>> getDepartmentList() {
        Log.i(TAG, "获取Department LiveData<List<Department>>列表 ");
        return departmentRepository.getDepartmentList();
    }

    public LiveData<List<Major>> getMajorList(int departmentId) {
        Log.i(TAG, "通过DepartmentId 获取Major List ");
        return majorRepository.getMajorListByDepartmentId(departmentId);
    }

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<String>();
            account.setValue("");
        }
        Log.i(TAG, "获取当前输入的用户Account ");
        return account;
    }

    public void setAccount(MutableLiveData<String> account) {
        Log.i(TAG, "保存当前输入的用户Account ");
        this.account = account;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<String>();
            password.setValue("");
        }
        Log.i(TAG, "获取当前输入的用户Password ");
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        Log.i(TAG, "保存当前输入的用户Password ");
        this.password = password;
    }

    public MutableLiveData<String> getCheckPassword() {
        if (checkPassword == null) {
            checkPassword = new MutableLiveData<String>();
            checkPassword.setValue("");
        }
        Log.i(TAG, "获取当前输入的用户CheckPassword ");
        return checkPassword;
    }

    public void setCheckPassword(MutableLiveData<String> checkPassword) {
        Log.i(TAG, "保存当前输入的用户CheckPassword ");
        this.checkPassword = checkPassword;
    }

    public MutableLiveData<Integer> getGender() {
        if (gender == null) {
            gender = new MutableLiveData<Integer>();
            gender.setValue(1);
        }
        Log.i(TAG, "获取当前用户选择的gender ");
        return gender;
    }

    public void setGender(int gender) {
        Log.i(TAG, "保存当前用户选择的gender ");
        this.gender.setValue(gender);
    }

    public MutableLiveData<String> getPhone() {
        if (phone == null) {
            phone = new MutableLiveData<String>();
            phone.setValue("");
        }
        Log.i(TAG, "获取当前输入的用户Phone ");
        return phone;
    }

    public void setPhone(MutableLiveData<String> phone) {
        Log.i(TAG, "保存当前输入的用户Phone ");
        this.phone = phone;
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<String>();
            name.setValue("");
        }
        Log.i(TAG, "获取当前输入的用户Name ");
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        Log.i(TAG, "保存当前输入的用户Name ");
        this.name = name;
    }

    public MutableLiveData<Integer> getDepartmentId() {
        if (departmentId == null) {
            departmentId = new MutableLiveData<Integer>();
            departmentId.setValue(1);
        }
        Log.i(TAG, "获取当前用户选择的department ");
        return departmentId;
    }

    public void setDepartmentId(MutableLiveData<Integer> departmentId) {
        Log.i(TAG, "保存当前用户选择的department ");
        this.departmentId = departmentId;
    }

    public MutableLiveData<Integer> getMajorId() {
        if (majorId == null) {
            majorId = new MutableLiveData<>();
            majorId.setValue(1);
        }
        Log.i(TAG, "获取当前用户选择的major ");
        return majorId;
    }

    public void setMajorId(MutableLiveData<Integer> majorId) {
        Log.i(TAG, "保存当前用户选择的major ");
        this.majorId = majorId;
    }

    public MutableLiveData<String> getClasses() {
        if (classes == null) {
            classes = new MutableLiveData<String>();
            classes.setValue("");
        }
        Log.i(TAG, "获取当前用户输入的班级 ");
        return classes;
    }

    public void setClasses(MutableLiveData<String> classes) {
        Log.i(TAG, "保存当前用户输入的班级 ");
        this.classes = classes;
    }


    private void saveUser(User user){
        Log.i(TAG, "保存用户的信息至SP");
        userRepository.saveUserMessage(user);
    }

    public LiveData<User> queryUserByAccount(int account){
        Log.i(TAG, "通过account查询用户信息，在Register中用来查询用户名是否存在");
        return userRepository.queryUserByAccount(account);
    }

    public void registerUser(User user){
        Log.i(TAG, "注册用户");
        userRepository.register(user);
        saveUser(user);
    }
}
