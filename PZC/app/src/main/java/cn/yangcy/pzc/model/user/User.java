package cn.yangcy.pzc.model.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_user")
public class User {

    @ColumnInfo(name = "account")
    @NonNull
    @PrimaryKey
    private int account;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "department")
    private String department;

    @ColumnInfo(name = "major")
    private String major;

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
    public User(int account, String password, String name, String phoneNumber, String department, String major, String classes) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.major = major;
        this.classes = classes;
    }

    public User(int account, String password, String name, String phoneNumber, String department, String major, String classes, int power) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.major = major;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public String toPersonInCharge(){
        String years = String.valueOf(account).substring(2,4);
        return  department+"\n20"+years+"级"+major+" "+name;
    }

    public String getUserInfo(){
        String years = String.valueOf(account).substring(2,4);
        return  department+"\n20"+years+"级"+major;
    }
}
