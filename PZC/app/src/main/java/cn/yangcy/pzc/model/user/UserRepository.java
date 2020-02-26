package cn.yangcy.pzc.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.model.department.DepartmentDao;
import cn.yangcy.pzc.viewmodel.LoginViewModel;


public class UserRepository {

    private final static String TAG = "UserRepository";
    private SharedPreferences sharedPreferences;
    private UserDao userDao;

    public UserRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        userDao = dataBase.getUserDao();
        Log.i(TAG, "UserRepository: DB connect");
        sharedPreferences = context.getSharedPreferences(Config.SP_NAME,Context.MODE_PRIVATE);
    }

    public void register(User... users){
        new RegisterAsync(userDao).execute(users);
    }

    public String getOrganizationPersonInChargeName(int account){
       return userDao.queryPersonOfOrganizationCharge(account).toPersonInCharge();
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
        Log.d(TAG, "queryUser: ");
        //主线程执行
        return userDao.queryUser(account);
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
}
