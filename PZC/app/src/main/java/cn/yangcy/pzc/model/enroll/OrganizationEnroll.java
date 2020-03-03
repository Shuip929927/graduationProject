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
        foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id"),
                @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id")} ,
        indices ={@Index(value = "user_id"),@Index("organization_id")} )

public class OrganizationEnroll {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
//    @ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id")
    private int userAccount;

    @ColumnInfo(name = "organization_id")
//    @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id")
    private int organizationId;

    @ColumnInfo(name = "organization_enroll_state",defaultValue = "1")
    private int state;
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

    public OrganizationEnroll(int userAccount, int organizationId) {
        this.userAccount = userAccount;
        this.organizationId = organizationId;
    }

    @Ignore
    public OrganizationEnroll(int userAccount, int organizationId, int state) {
        this.userAccount = userAccount;
        this.organizationId = organizationId;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
//
//    public int getPower() {
//        return power;
//    }
//
//    public void setPower(int power) {
//        this.power = power;
//    }
}
