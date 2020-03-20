package cn.yangcy.pzc.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.fragment.stundentunoin.OrganizationEnrollPage;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.model.department.DepartmentDao;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.net.HttpUtil;
import cn.yangcy.pzc.util.JsonResult;
import cn.yangcy.pzc.viewmodel.LoginViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class UserRepository {

    private final static String TAG = "UserRepository";
    private SharedPreferences sharedPreferences;
    private MajorRepository majorRepository;
    private DepartmentRepository departmentRepository;
    private UserDao userDao;
    private LiveData<List<User>> userLiveData;

    public UserRepository(Context context) {
        majorRepository = new MajorRepository(context);
        departmentRepository = new DepartmentRepository(context);
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        userDao = dataBase.getUserDao();
        Log.i(TAG, "UserRepository: DB connect");
        sharedPreferences = context.getSharedPreferences(Config.SP_NAME,Context.MODE_PRIVATE);
    }

    public void register(User... users){
        new RegisterAsync(userDao).execute(users);
    }

    public String getOrganizationPersonInChargeName(int account){
//       return userDao.queryPersonOfOrganizationCharge(account).toPersonInCharge();
        String result = "";
        User user =  userDao.queryPersonOfOrganizationCharge(account);
        String department = departmentRepository.getDepartmentNameById(user.getDepartmentId());
        String major = majorRepository.getMajorNameById(user.getMajorId());
    //TODO :未完成修改
        result = department+ " "+ user.getYear()+"\n"+major +"\n"+user.getName();
        return result;
    }

    public String getOrganizationMemberInfo(int account){
//        return userDao.queryPersonOfOrganizationCharge(account).getUserInfo();
        String result = "";
        User user = userDao.queryPersonOfOrganizationCharge(account);
        String department = departmentRepository.getDepartmentNameById(user.getDepartmentId());
        String major = majorRepository.getMajorNameById(user.getMajorId());
    //TODO :未完成修改
        result =  user.getYear()+" "+department+" \n "+major;
        return result;
    }

    public void saveUserMessage(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_account",user.getAccount());
        editor.putInt("user_power",user.getPower());
        editor.apply();
    }

    static class RegisterAsync extends AsyncTask<User,Void,Void> {
        private UserDao userDao;

        public RegisterAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            Log.i(TAG, "doInBackground: RegisterAsync");
            userDao.insertUser(users[0]);
            return null;
        }
    }

    public User queryUser(String account){
//    public MutableLiveData<User> queryUser(String account){
        //异步查询获取数据
//        QueryAsyncTask queryAsyncTask = new QueryAsyncTask(userDao);
////        queryAsyncTask.execute(string);
////        queryAsyncTask.setOnAsyncResponse(new AsyncResponse() {
////            @Override
////            public void onDataReceivedSuccess(Object object) {
////                resultUser = (User) object;
////                Log.d(TAG, "onDataReceivedSuccess: "+resultUser.toString());
////
////            }
////
////            @Override
////            public void onDataReceivedFailed() {
////                Log.d(TAG, "onDataReceivedFailed: null");
////                resultUser = null;
////            }
////
////        });
////        return resultUser;

//        final MutableLiveData<User> resultUser = null;
//
//        HttpUtil.sendOkHttpRequest(Config.USER_QUERY_ACCOUNT + account,
//                new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        resultUser.setValue(null);
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Gson gson = new Gson();
//                        String res = response.body().string();
//                        Log.i(TAG, "onResponse: "+ res);
//                        JsonResult jsonResult = gson.fromJson(res, JsonResult.class);
//                        Log.i(TAG, "onResponse: " + jsonResult.getData().toString());
//                        resultUser.setValue((User) jsonResult.getData());
//                    }
//                });

        Log.d(TAG, "queryUser: ");
        //主线程执行
        return userDao.queryUser(account);
//        return resultUser;
    }

    public LiveData<List<User>> getUserLiveData(List<Integer> list) {
        Log.i(TAG, "getUserLiveData");
        int[] accounts = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "getUserLiveData: "+ list.get(i));
            accounts[i] = list.get(i);
        }
        userLiveData = userDao.queryOrganizationMember(accounts);
        return userLiveData;
    }

    public void updateUser(User user){
        Log.i(TAG, "updateUser");
        userDao.updateUser(user);
    }

    //异步查询类
    /**
     * 因异步查询doInBackground方法还没执行，
     * 主线程就已经判断user为空，暂无解决方法
     * 只能让数据库允许在主线程执行操作，
     * 将该查询在主线程中执行
     * 因此注释*/
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


//    public void insertUser(User user){
//        new InsertAsyncTask(userDao).execute(user);
//        Log.d(TAG, "insertUser: "+user.toString());
//    }
//
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

    //TODO:测试MutableLiveData 转换成LiveData 输出 成功！
//    private MutableLiveData<User> users;
//    public LiveData<User> getTestTest() {
//        if (users == null) {
//            users = new MutableLiveData<>();
//            loadUsers();
//        }
//        return users;
//    }
//
//    private void loadUsers() {
//        // Do an asynchronous operation to fetch users.
//        User u = new User(1234,"test","test","test","test","test","1");
//        users.setValue(u);
//    }
}
