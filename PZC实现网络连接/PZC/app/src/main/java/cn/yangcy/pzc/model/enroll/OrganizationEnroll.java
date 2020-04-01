package cn.yangcy.pzc.model.enroll;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.User;

@Entity(tableName = "tb_organization_enroll",
        foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id",
                onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id",
                        onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)} ,
        indices ={@Index(value = "user_id"),@Index("organization_id")} )

public class OrganizationEnroll {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
//    @ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id")
    private int user_id;

    @ColumnInfo(name = "organization_id")
//    @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id")
    private int organization_id;

    @ColumnInfo(name = "organization_enroll_state",defaultValue = "1")
    private int organization_enroll_state;
    /*
     *  不存在 代表未报名，
     *  0 代表 审核不通过 ，
     *  1 代表已经报名 但未审核 ，
     *  2 代表通过审核
     * */
//
//    @ColumnInfo(name = "user_power",defaultValue = "2")
//    private int power;
    /*
     *  2 代表学生组织成员
     *  3 代表学生组织负责人
     *  4 代表学生会主席团
     *  5 代表老师
     *  6 代表管理员
     * */

    @Ignore
    public OrganizationEnroll() {
    }

    public OrganizationEnroll(int user_id, int organization_id) {
        this.user_id = user_id;
        this.organization_id = organization_id;
        this.organization_enroll_state = 0;
    }

    @Ignore
    public OrganizationEnroll(int user_id, int organization_id, int organization_enroll_state) {
        this.user_id = user_id;
        this.organization_id = organization_id;
        this.organization_enroll_state = organization_enroll_state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public int getOrganization_enroll_state() {
        return organization_enroll_state;
    }

    public void setOrganization_enroll_state(int organization_enroll_state) {
        this.organization_enroll_state = organization_enroll_state;
    }
//
//    public int getPower() {
//        return power;
//    }
//
//    public void setPower(int power) {
//        this.power = power;
//    }


    @Override
    public String toString() {
        return "OrganizationEnroll{" +
                "id=" + id +
                ", userAccount=" + user_id +
                ", organizationId=" + organization_id +
                ", state=" + organization_enroll_state +
                '}';
    }
}
