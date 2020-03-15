package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.major.Major;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;
import cn.yangcy.pzc.util.MD5Util;

public class RegisterViewModel extends AndroidViewModel {

    private static final String TAG = "RegisterViewModel";
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> checkPassword;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> name;
    private MutableLiveData<Integer> departmentId;
    private MutableLiveData<String> major;
    private MutableLiveData<String> classes;

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        majorRepository = new MajorRepository(application);
    }

    public List<String> getDepartmentList() {
        return departmentRepository.getDepartmentList();
    }

    public List<String> getMajorList() {
        return majorRepository.getMajorListByDepartmentId(1);
    }
    public List<String> getMajorList(int departmentId) {
        return majorRepository.getMajorListByDepartmentId(departmentId);
    }

    public int getMajorIdByName(String majorName){
        return majorRepository.getMajorIdByName(majorName);
    }

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<String>();
            account.setValue("");
        }
        return account;
    }

    public void setAccount(MutableLiveData<String> account) {
        this.account = account;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<String>();
            password.setValue("");
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public MutableLiveData<String> getCheckPassword() {
        if (checkPassword == null) {
            checkPassword = new MutableLiveData<String>();
            checkPassword.setValue("");
        }
        return checkPassword;
    }

    public void setCheckPassword(MutableLiveData<String> checkPassword) {
        this.checkPassword = checkPassword;
    }

    public MutableLiveData<String> getPhone() {
        if (phone == null) {
            phone = new MutableLiveData<String>();
            phone.setValue("");
        }
        return phone;
    }

    public void setPhone(MutableLiveData<String> phone) {
        this.phone = phone;
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<String>();
            name.setValue("");
        }
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<Integer> getDepartmentId() {
        if (departmentId == null) {
            departmentId = new MutableLiveData<Integer>();
            departmentId.setValue(-1);
        }
        return departmentId;
    }

    public void setDepartmentId(MutableLiveData<Integer> departmentId) {
        this.departmentId = departmentId;
    }

    public MutableLiveData<String> getMajor() {
        if (major == null) {
            major = new MutableLiveData<String>();
            major.setValue("");
        }
        return major;
    }

    public void setMajor(MutableLiveData<String> major) {
        this.major = major;
    }

    public MutableLiveData<String> getClasses() {
        if (classes == null) {
            classes = new MutableLiveData<String>();
            classes.setValue("");
        }
        return classes;
    }

    public void setClasses(MutableLiveData<String> classes) {
        this.classes = classes;
    }

    public boolean doRegister() {
        Log.i(TAG, "doRegister: name" + name.getValue());
        Log.i(TAG, "doRegister: department" + departmentId.getValue());
        Log.i(TAG, "doRegister: major" + major.getValue());
        Log.i(TAG, "doRegister: classes" + classes.getValue());
//        if(departmentList == null){
//            Log.i(TAG, "departmentList: isEmpty");
//        } else {
//            Log.i(TAG, "departmentList: NotNUll");
//            for (int i = 0; i < departmentList.size(); i++) {
//                Log.i(TAG, "departmentList: " + departmentList.get(i));
//            }
//        }

        if (name.getValue().isEmpty() || departmentId.getValue()< 0 ||
                major.getValue().isEmpty() || classes.getValue().isEmpty()) {
            return false;
        } else {
            User user = new User(Integer.valueOf(account.getValue()), MD5Util.digest(password.getValue()), name.getValue(),
                    MD5Util.digest(phone.getValue()), departmentId.getValue(), majorRepository.getMajorIdByName(major.getValue()), classes.getValue(), 1);
            Log.i(TAG, "doRegister: " + user.toString());
            userRepository.register(user);
            userRepository.saveUserMessage(user);
            return true;
        }


    }

    public boolean doNext() {
        Log.i(TAG, "doNext: account" + account.getValue());
        Log.i(TAG, "idoNext: password" + password.getValue());
        Log.i(TAG, "doNext: checkPassword" + checkPassword.getValue());
        Log.i(TAG, "doNext: phone" + phone.getValue());
        if (account.getValue().isEmpty() || password.getValue().isEmpty() ||
                checkPassword.getValue().isEmpty() || phone.getValue().isEmpty()) {
            Toast.makeText(getApplication(), R.string.err_input_cannot_empty, Toast.LENGTH_SHORT).show();
            return false;
        } else {
//            if(account.getValue().length()!=9){
//                Toast.makeText(getApplication(), R.string.et_account_hint,Toast.LENGTH_SHORT).show();
//                return false;
//            }

            User u = userRepository.queryUser(account.getValue());

            if (u != null) {
                Toast.makeText(getApplication(), R.string.err_user_exist, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!password.getValue().equals(checkPassword.getValue())) {
                Toast.makeText(getApplication(), R.string.err_password_not_equals_check, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (phone.getValue().length() != 11) {
                Toast.makeText(getApplication(), R.string.err_phone_lenth, Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

}
