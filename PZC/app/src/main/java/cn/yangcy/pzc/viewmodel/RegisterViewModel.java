package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentDao;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserDao;
import cn.yangcy.pzc.model.user.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private String TAG = "mRegisterViewModel";
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> checkPassword;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> name;
    private MutableLiveData<String> department;
    private MutableLiveData<String> major;
    private MutableLiveData<String> classes;

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
    }

    public List<String> getDepartmentList(){
        return departmentRepository.getDepartmentList();
    }

    public MutableLiveData<String> getAccount() {
        if(account == null){
            account = new MutableLiveData<String>();
            account.setValue("1");
        }
        return account;
    }

    public void setAccount(MutableLiveData<String> account) {
        this.account = account;
    }

    public MutableLiveData<String> getPassword() {
        if(password == null){
            password = new MutableLiveData<String>();
            password.setValue("2");
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public MutableLiveData<String> getCheckPassword() {
        if(checkPassword == null){
            checkPassword = new MutableLiveData<String>();
            checkPassword.setValue("3");
        }
        return checkPassword;
    }

    public void setCheckPassword(MutableLiveData<String> checkPassword) {
        this.checkPassword = checkPassword;
    }

    public MutableLiveData<String> getPhone() {
        if(phone == null){
            phone = new MutableLiveData<String>();
            phone.setValue("4");
        }
        return phone;
    }

    public void setPhone(MutableLiveData<String> phone) {
        this.phone = phone;
    }

    public MutableLiveData<String> getName() {
        if(name == null){
            name = new MutableLiveData<String>();
            name.setValue("5");
        }
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<String> getDepartment() {
        if(department == null){
            department = new MutableLiveData<String>();
            department.setValue("6");
        }
        return department;
    }

    public void setDepartment(MutableLiveData<String> department) {
        this.department = department;
    }

    public MutableLiveData<String> getMajor() {
        if(major == null){
            major = new MutableLiveData<String>();
            major.setValue("7");
        }
        return major;
    }

    public void setMajor(MutableLiveData<String> major) {
        this.major = major;
    }

    public MutableLiveData<String> getClasses() {
        if(classes == null){
            classes = new MutableLiveData<String>();
            classes.setValue("8");
        }
        return classes;
    }

    public void setClasses(MutableLiveData<String> classes) {
        this.classes = classes;
    }

    public void doRegister(){
        doNext();
        Log.i(TAG, "doRegister: name"+ name.getValue());
        Log.i(TAG, "doRegister: department"+ department.getValue());
        Log.i(TAG, "doRegister: major"+ major.getValue());
        Log.i(TAG, "doRegister: classes"+ classes.getValue());
//        if(departmentList == null){
//            Log.i(TAG, "departmentList: isEmpty");
//        } else {
//            Log.i(TAG, "departmentList: NotNUll");
//            for (int i = 0; i < departmentList.size(); i++) {
//                Log.i(TAG, "departmentList: " + departmentList.get(i));
//            }
//        }
//        User user = new User(account.getValue(), MD5Util.digest(password.getValue()),name.getValue(),
//                MD5Util.digest(phone.getValue()),department.getValue(),major.getValue(),classes.getValue(),1);
//        Log.i(TAG, "doRegister: "+user.toString());
//        new RegisterAsync(userDao).execute(user);

    }

    public void doNext(){
        Log.i(TAG, "doNext: account"+ account.getValue());
        Log.i(TAG, "idoNext: password"+ password.getValue());
        Log.i(TAG, "doNext: checkPassword"+ checkPassword.getValue());
        Log.i(TAG, "doNext: phone"+ phone.getValue());
    }

}
