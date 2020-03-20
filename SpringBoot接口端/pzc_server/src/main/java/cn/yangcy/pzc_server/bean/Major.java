package cn.yangcy.pzc_server.bean;

public class Major {

    private Integer id;
    private Integer department_id;
    private String major_name;

    public Major() {
    }

    public Major(Integer department_id, String major_name) {
        this.department_id = department_id;
        this.major_name = major_name;
    }

    public Major(Integer id, Integer department_id, String major_name) {
        this.id = id;
        this.department_id = department_id;
        this.major_name = major_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }
}
