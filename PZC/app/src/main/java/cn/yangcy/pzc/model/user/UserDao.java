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

    @Query("SELECT * FROM tb_user WHERE account = :userAccount")
    User queryUser(String userAccount);

    @Query("SELECT * FROM tb_user ORDER BY account DESC")
    LiveData<List<User>> queryAllUser();

    @Query("SELECT * FROM tb_user WHERE account = :userAccount")
    User queryPersonOfOrganizationCharge(int userAccount);

}
