package cn.yangcy.pzc.model.organization;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import cn.yangcy.pzc.model.user.User;

@Entity(tableName = "tb_organization",indices = {@Index(value = "id")})
public class Organization implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "organization_name")
    private String organization;

    @ColumnInfo(name = "person_in_charge")
    private String personInCharge;

    @ColumnInfo(name = "person_id")
    @ForeignKey(entity = User.class,parentColumns = "account",childColumns = "person_id",
            onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.SET_NULL)
    private int personAccount;

    @ColumnInfo(name = "number_of_people",defaultValue = "0")
    private int numberOfPeople;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "isEnroll",defaultValue = "0")
    private int isEnroll;
    /*
     * 0 代表纳新 进行中
     * 1 代表活动结束
     * */


    public Organization(String organization, String personInCharge, String description) {
        this.organization = organization;
        this.personInCharge = personInCharge;
        this.description = description;
    }

    @Ignore
    public Organization(String organization, String personInCharge, int personAccount, int numberOfPeople, String description) {
        this.organization = organization;
        this.personInCharge = personInCharge;
        this.personAccount = personAccount;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public int getPersonAccount() {
        return personAccount;
    }

    public void setPersonAccount(int personAccount) {
        this.personAccount = personAccount;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
