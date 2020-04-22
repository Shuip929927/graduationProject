package cn.yangcy.pzc_server.bean;

public class OrganizationEnroll {

    private Integer id;
    private Integer user_id;
    private Integer organization_id;
    private Integer organization_enroll_state;

    private OrganizationEnroll(){

    }

    public OrganizationEnroll(Integer user_id, Integer organization_id) {
        this.user_id = user_id;
        this.organization_id = organization_id;
    }

    public OrganizationEnroll(Integer user_id, Integer organization_id, Integer organization_enroll_state) {
        this.user_id = user_id;
        this.organization_id = organization_id;
        this.organization_enroll_state = organization_enroll_state;
    }

    public OrganizationEnroll(Integer id, Integer user_id, Integer organization_id, Integer organization_enroll_state) {
        this.id = id;
        this.user_id = user_id;
        this.organization_id = organization_id;
        this.organization_enroll_state = organization_enroll_state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUserID(Integer userId) {
        this.user_id = userId;
    }

    public Integer getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Integer organization_id) {
        this.organization_id = organization_id;
    }

    public Integer getOrganization_enroll_state() {
        return organization_enroll_state;
    }

    public void setOrganization_enroll_state(Integer organization_enroll_state) {
        this.organization_enroll_state = organization_enroll_state;
    }

    @Override
    public String toString() {
        return "OrganizationEnroll[" +
                "id=" + id +
                ", user_id=" + user_id +
                ", organization_id=" + organization_id +
                ", organization_enroll_state=" + organization_enroll_state +
                ']';
    }
}
