package cn.yangcy.pzc_server.bean;

public class User {

    private Integer account;
    private String password;
    private Integer gender;
    // 1 male 2 female
    private String name;
    private String phoneNumber;
    private Integer departmentId;
    private Integer majorId;
    private String classes;
    private Integer power;
    /*
     *  1 代表没有参加任何学生组织的学生
     *  2 代表学生组织成员
     *  3 代表学生组织负责人
     *  4 代表学生会主席团
     *  5 代表老师
     *  6 代表管理员
     * */

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }


    public User(Integer account, String password, Integer gender, String name,
                String phoneNumber, Integer departmentId, Integer majorId, String classes, Integer power) {
        this.account = account;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.majorId = majorId;
        this.classes = classes;
        this.power = power;
    }

    public User(Integer account, String password, Integer gender, String name,
                String phoneNumber, Integer departmentId, Integer majorId, String classes) {
        this.account = account;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.majorId = majorId;
        this.classes = classes;
        this.power = 1;
    }

    public User(){

    }

//    public User(Integer account, String password) {
//        this.account = account;
//        this.password = password;
//    }

    @Override
    public String toString() {
        return "User[" +
                "account=" + account +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", classes='" + classes + '\'' +
                ", power=" + power +
                ']';
    }
}
