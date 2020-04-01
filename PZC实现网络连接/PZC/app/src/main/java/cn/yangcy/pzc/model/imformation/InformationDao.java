package cn.yangcy.pzc.model.imformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InformationDao {

    @Insert
    void insertInformation(Information... information);

    @Delete
    void deleteInformation(Information... information);


    @Update
    void updateInformation(Information information);

    //查询所有信息
    @Query("SELECT * FROM tb_information WHERE isDelete = 0 ORDER BY createOn DESC")
    LiveData<List<Information>> queryNewInfo();

    //分页加载信息
    @Query("SELECT * FROM tb_information WHERE isDelete = 0 ORDER BY createOn DESC")
    DataSource.Factory<Integer,Information> queryNewInfoByPaging();


    @Query("SELECT * FROM tb_information WHERE isDelete = 0 ORDER BY hits DESC")
    LiveData<List<Information>> queryHotInfo();


    @Query("SELECT * FROM tb_information WHERE isDelete = 0 ORDER BY hits DESC")
    DataSource.Factory<Integer,Information> queryHotInfoByPaging();

}
