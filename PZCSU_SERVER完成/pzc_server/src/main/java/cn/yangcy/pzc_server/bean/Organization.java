package cn.yangcy.pzc_server.bean;

public class Organization {

    private Integer id;

    private String organization_name;

    private String person_in_charge;

    private String person_id;

    private Integer number_of_people;

    private String description;

    private Integer isEnroll;

    public Organization() {
    }

    public Organization(Integer id, String organization_name, String person_in_charge,
                        String person_id, Integer number_of_people, String description, Integer isEnroll) {
        this.id = id;
        this.organization_name = organization_name;
        this.person_in_charge = person_in_charge;
        this.person_id = person_id;
        this.number_of_people = number_of_people;
        this.description = description;
        this.isEnroll = isEnroll;
    }

    public Organization(String organization_name, String person_in_charge,
                        String person_id, Integer number_of_people, String description) {
        this.organization_name = organization_name;
        this.person_in_charge = person_in_charge;
        this.person_id = person_id;
        this.number_of_people = number_of_people;
        this.description = description;
        this.isEnroll = 0;
    }

    public Organization(String organization_name, String person_in_charge,
                        String person_id, Integer number_of_people, String description, Integer isEnroll) {
        this.organization_name = organization_name;
        this.person_in_charge = person_in_charge;
        this.person_id = person_id;
        this.number_of_people = number_of_people;
        this.description = description;
        this.isEnroll = isEnroll;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getPerson_in_charge() {
        return person_in_charge;
    }

    public void setPerson_in_charge(String person_in_charge) {
        this.person_in_charge = person_in_charge;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public Integer getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(Integer number_of_people) {
        this.number_of_people = number_of_people;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsEnroll() {
        return isEnroll;
    }

    public void setIsEnroll(Integer isEnroll) {
        this.isEnroll = isEnroll;
    }

    @Override
    public String toString() {
        return "Organization[" +
                "id=" + id +
                ", organization_name='" + organization_name + '\'' +
                ", person_in_charge='" + person_in_charge + '\'' +
                ", person_id='" + person_id + '\'' +
                ", number_of_people=" + number_of_people +
                ", description='" + description + '\'' +
                ", isEnroll=" + isEnroll +
                ']';
    }
}
