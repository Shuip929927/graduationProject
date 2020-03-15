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
    private int departmentId;

    @ColumnInfo(name = "major_name")
    private String majorName;

    public Major(int departmentId, String majorName) {
        this.departmentId = departmentId;
        this.majorName = majorName;
    }

    @Ignore
    public Major(int id, int departmentId, String majorName) {
        this.id = id;
        this.departmentId = departmentId;
        this.majorName = majorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
