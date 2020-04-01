package cn.yangcy.pzc.model.organization;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import cn.yangcy.pzc.model.user.User;

@Entity(tableName = "tb_organization", indices = {@Index(value = "id")})
public class Organization implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "organization_name")
    private String organization_name;

    @ColumnInfo(name = "person_in_charge")
    private String person_in_charge;

    @ColumnInfo(name = "person_id")
    @ForeignKey(entity = User.class, parentColumns = "account", childColumns = "person_id",
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.SET_NULL)
    private int person_id;

    @ColumnInfo(name = "number_of_people", defaultValue = "0")
    private int number_of_people;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "isEnroll", defaultValue = "0")
    private int isEnroll;
    /*
     * 0 代表纳新 进行中
     * 1 代表活动结束
     * */

    @Ignore
    public Organization() {
    }

    public Organization(String organization_name, String person_in_charge, String description) {
        this.organization_name = organization_name;
        this.person_in_charge = person_in_charge;
        this.description = description;
    }

    @Ignore
    public Organization(String organization_name, String person_in_charge, int person_id, int number_of_people, String description) {
        this.organization_name = organization_name;
        this.person_in_charge = person_in_charge;
        this.person_id = person_id;
        this.number_of_people = number_of_people;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(int number_of_people) {
        this.number_of_people = number_of_people;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsEnroll() {
        return isEnroll;
    }

    public void setIsEnroll(int isEnroll) {
        this.isEnroll = isEnroll;
    }


}
