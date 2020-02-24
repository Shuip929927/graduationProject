package cn.yangcy.pzc.model.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cn.yangcy.pzc.model.organization.Organization;

@Entity(tableName = "tb_activities")
public class Activities {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "organization_id")
    @ForeignKey(entity = Organization.class,parentColumns = "id",childColumns = "organization_id")
    private int organizationId;

    @ColumnInfo(name = "organization_name")
    private String organizationName;

    @ColumnInfo(name = "activity_name")
    private String name;

    @ColumnInfo(name = "activity_start_time")
    private String startTime;

    @ColumnInfo(name = "activity_description")
    private String description;

    @ColumnInfo(name = "activity_state",defaultValue = "0")
    private int state;

    @ColumnInfo(name = "isDelete",defaultValue = "0")
    private int isDelete;

    public Activities(int organizationId, String organizationName, String name, String startTime, String description) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.name = name;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
