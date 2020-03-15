package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;
import cn.yangcy.pzc.util.MD5Util;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private UserRepository userRepository;

    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private User resultUser;

    //    测试数据绑定是否成功
//    private MutableLiveData<String> result;
    private LiveData<List<User>> resultU;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "LoginViewModel: getDatabase");
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<String>();
            account.setValue("1234");
            Log.d(TAG, "getAccount: " + account.getValue());
        }
        return account;
    }

    public void setAccount(MutableLiveData<String> account) {
        this.account = account;
        Log.d(TAG, "setAccount: " + account.getValue());
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<String>();
            password.setValue("1234");
            Log.d(TAG, "getPassword: " + password.getValue());
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
        Log.d(TAG, "setPassword: " + password.getValue());
    }

//    测试数据绑定是否成功
//    public LiveData<List<User>> getAllUser(){
//        resultU = userDao.queryAllUser();
//        Log.d(TAG, "getAllUser: ");
//        return resultU;
//    }

//    测试数据绑定是否成功
//    public MutableLiveData<String> getResult() {
//        if(result == null){
//            result = new MutableLiveData<String> ();
//            result.setValue("123");
//        }
//        return result;
//    }
//
//    public void setResult(MutableLiveData<String> result) {
//        this.result = result;
//    }

    public boolean doLogin() {
        //测试数据绑定是否成功
        Log.d(TAG, "doLogin:  doLogin");

        if(account.getValue().isEmpty()||password.getValue().isEmpty()){
            Toast.makeText(getApplication(), R.string.err_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        User u = userRepository.queryUser(account.getValue());

        if (u == null) {
            Log.d(TAG, "doLogin: queryUser Null");
            Toast.makeText(getApplication(), R.string.err_user_no_exist, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(MD5Util.digest(password.getValue()).equals(u.getPassword())){
                Log.d(TAG, "doLogin: queryUser Exist");
                Log.d(TAG, "doLogin: "+ u.toString());
                userRepository.saveUserMessage(u);
                return true;
            } else{
                Toast.makeText(getApplication(), R.string.err_password, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }
//优化代码后将下列代码移至UserRepository
// 测试后功能正常

//    public void insertUser(User user){
//        new InsertAsyncTask(userDao).execute(user);
//        Log.d(TAG, "insertUser: "+user.toString());
//    }

//    static class InsertAsyncTask extends AsyncTask<User,Void,Void>{
//        private UserDao userDao;
//
//        public InsertAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected Void doInBackground(User... users) {
//            Log.d(TAG, "doInBackground: ");
//            userDao.insertUser(users[0]);
//            return null;
//        }
//    }

//    public User queryUser(String string){
    //异步查询获取数据
//        QueryAsyncTask queryAsyncTask = new QueryAsyncTask(userDao);
//        queryAsyncTask.execute(string);
//        queryAsyncTask.setOnAsyncResponse(new AsyncResponse() {
//            @Override
//            public void onDataReceivedSuccess(Object object) {
//                resultUser = (User) object;
//                Log.d(TAG, "onDataReceivedSuccess: "+resultUser.toString());
//
//            }
//
//            @Override
//            public void onDataReceivedFailed() {
//                Log.d(TAG, "onDataReceivedFailed: null");
//                resultUser = null;
//            }
//
//        });
//        return resultUser;
//        Log.d(TAG, "queryUser: ");
    //主线程执行
//        return userDao.queryUser(string);
}


//异步查询类
/**
 * 因异步查询doInBackground方法还没执行，
 * 主线程就已经判断user为空，暂无解决方法
 * 只能让数据库允许在主线程执行操作，
 * 将该查询在主线程中执行
 * 因此注释
 */
//    static class QueryAsyncTask extends AsyncTask<String,Void,User>{
//        private UserDao userDao;
//        private User resultUser;
//        public AsyncResponse asyncResponse;
//
//        public QueryAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//        public void setOnAsyncResponse(AsyncResponse asyncResponse)
//        {
//            this.asyncResponse = asyncResponse;
//        }
//
//        @Override
//        protected User doInBackground(String... strings) {
//            Log.d(TAG, "doInBackground: ");
//            resultUser = userDao.queryUser(strings[0]);
//            return resultUser;
//        }
//
//        @Override
//        protected void onPostExecute(User user) {
//            Log.d(TAG, "onPostExecute: ");
//            super.onPostExecute(user);
//            if(user != null){
//                asyncResponse.onDataReceivedSuccess(user);
//            } else {
//                asyncResponse.onDataReceivedFailed();
//            }
//        }
//    }
//}
