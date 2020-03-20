package cn.yangcy.pzc.model.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tb_user",indices = {@Index(value = "account")})
public class User implements Serializable {

    @Ignore
    private static final long serialVersionUID = -7325123912015795057L;

    @ColumnInfo(name = "account")
    @PrimaryKey
    private int account;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "department_id")
    private int departmentId;

    @ColumnInfo(name = "major_id")
    private int majorId;

    @ColumnInfo(name = "classes")
    private String classes;

    @ColumnInfo(name = "power",defaultValue = "1")
    private int power;
    /*
     *  1 代表没有参加任何学生组织的学生
     *  2 代表学生组织成员
     *  3 代表学生组织负责人
     *  4 代表学生会主席团
     *  5 代表老师
     *  6 代表管理员
     * */

    @Ignore
    public User() {
    }

    @Ignore
    public User(int account, String password, String name, String phoneNumber, int departmentId, int majorId, String classes) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.majorId = majorId;
        this.classes = classes;
    }

    public User(int account, String password, String name, String phoneNumber, int departmentId, int majorId, String classes, int power) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.majorId = majorId;
        this.classes = classes;
        this.power = power;
    }


    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajor(int majorId) {
        this.majorId = majorId;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
//    //TODO :未完成修改
//    public String toPersonInCharge(){
//        int years = Integer.parseInt(String.valueOf(account).substring(0,4))-100;
//        return  years+"级";
//    }
//    //TODO :未完成修改
//    public String getUserInfo(){
//        int years = Integer.parseInt(String.valueOf(account).substring(0,4))-100;
//        return  years+"级";
//    }

    public String getYear(){
        int years = Integer.parseInt(String.valueOf(account).substring(0,4))-100;
        return  years+"级";
    }
}
