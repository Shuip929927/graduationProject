package cn.yangcy.pzc.model.major;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cn.yangcy.pzc.model.department.Department;

@Entity(tableName = "tb_major",indices = {@Index(value = "department_id")},foreignKeys = {@ForeignKey(entity = Department.class,
        parentColumns = "id", childColumns = "department_id",
        onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class Major {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "department_id")
    private int department_id;

    @ColumnInfo(name = "major_name")
    private String major_name;

    @Ignore
    public Major() {
    }

    public Major(int department_id, String major_name) {
        this.department_id = department_id;
        this.major_name = major_name;
    }

    @Ignore
    public Major(int id, int department_id, String major_name) {
        this.id = id;
        this.department_id = department_id;
        this.major_name = major_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }
}
