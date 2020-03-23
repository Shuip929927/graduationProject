package cn.yangcy.pzc.model.department;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_department",indices = {@Index(value = {"id", "department_name"},
        unique = true)})
public class Department {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "department_name")
    @NonNull
    private String department;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NonNull String department) {
        this.department = department;
    }

    @Ignore
    public Department() {
    }

    public Department(@NonNull String department) {
        this.department = department;
    }
}
