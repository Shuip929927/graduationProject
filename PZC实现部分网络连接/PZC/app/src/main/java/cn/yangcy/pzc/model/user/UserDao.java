package cn.yangcy.pzc.model.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.yangcy.pzc.model.user.User;

@Dao
public interface UserDao {

    @Insert()
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

    //    @Query("SELECT * FROM book "
//            + "INNER JOIN loan ON loan.book_id = book.id "
//            + "INNER JOIN user ON user.id = loan.user_id "
//            + "WHERE user.name LIKE :userName")
//    public List<Book> findBooksBorrowedByNameSync(String userName);
//    @Query("SELECT tb_user.* FROM tb_organization "
//            + "INNER JOIN tb_organization_enroll ON   tb_organization.id= tb_organization_enroll.organization_id "
//            + "INNER JOIN tb_user ON tb_organization_enroll.user_id = tb_user.account AND tb_organization_enroll.organization_enroll_state = 2"
//            + "WHERE tb_organization.id LIKE :organization")
//    public LiveData<List<User>> queryMemberList(int organization);
}
