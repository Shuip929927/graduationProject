package cn.yangcy.pzc.model.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import cn.yangcy.pzc.model.organization.Organization;

@Entity(tableName = "tb_activities",indices = {@Index(value = "id")})
public class Activities implements Serializable {

    @Ignore
    private static final long serialVersionUID = 5720168515016805735L;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "organization_id")
    @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id")
    private int organizationId;

//    @ColumnInfo(name = "organization_name")
//    private String organizationName;

    @ColumnInfo(name = "activity_name")
    private String name;

    @ColumnInfo(name = "activity_create_time")
    private String createOn;

    @ColumnInfo(name = "activity_start_time")
    private String startTime;

    @ColumnInfo(name = "activity_description")
    private String description;

    @ColumnInfo(name = "activity_state",defaultValue = "0")
    private int state;
    /*
    * 0 代表活动 进行中
    * 1 代表活动结束
    * 2 代表活动长期有效
    * */

    @ColumnInfo(name = "isDelete",defaultValue = "0")
    private int isDelete;
    /*
     * 0 代表活动存在
     * 1 代表活动删除
     * */

    public Activities(int organizationId, /*String organizationName*/ String name, String createOn,String startTime, String description) {
        this.organizationId = organizationId;
//        this.organizationName = organizationName;
        this.name = name;
        this.createOn = createOn;
        this.startTime = startTime;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

//    public String getOrganizationName() {
//        return organizationName;
//    }
//
//    public void setOrganizationName(String organizationName) {
//        this.organizationName = organizationName;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
