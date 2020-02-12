package cn.yangcy.pzc.model.imformation;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM tb_information ORDER BY createOn DESC")
    LiveData<List<Information>> queryNewInfo();

    @Query("SELECT * FROM tb_information ORDER BY hits DESC")
    LiveData<List<Information>> queryHotInfo();

}
