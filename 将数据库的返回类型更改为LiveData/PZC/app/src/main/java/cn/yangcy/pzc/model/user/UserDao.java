package cn.yangcy.pzc.model.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.yangcy.pzc.model.user.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    //通过账号查询用户
    @Query("SELECT * FROM tb_user WHERE account = :userAccount")
    LiveData<User> queryUserByAccount(int userAccount);

    //查询所有用户
    @Query("SELECT * FROM tb_user ORDER BY account DESC")
    LiveData<List<User>> queryAllUser();

    //查询一系列用户
    @Query("SELECT * FROM tb_user WHERE account in(:userAccounts) ORDER BY account DESC")
    LiveData<List<User>> queryMemberLiveList(int[] userAccounts);

}
