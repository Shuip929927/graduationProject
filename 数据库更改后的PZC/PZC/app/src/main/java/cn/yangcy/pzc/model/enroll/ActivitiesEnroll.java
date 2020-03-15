package cn.yangcy.pzc.model.enroll;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.user.User;

@Entity(tableName = "tb_activities_enroll",
        foreignKeys = { @ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id",
                        onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Activities.class,parentColumns = "id",childColumns = "activity_id",
                        onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)} ,
        indices = {@Index(value = "user_id"), @Index("activity_id")})
public class ActivitiesEnroll {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
//    @ForeignKey(entity = User.class,parentColumns = "account",childColumns = "user_id")
    private int userAccount;

    @ColumnInfo(name = "activity_id")
//    @ForeignKey(entity = Activities.class,parentColumns = "id",childColumns = "activity_id")
    private int activitiesId;

    @ColumnInfo(name = "activity_enroll_state",defaultValue = "0")
    private int state;
    /*
     *  不存在 代表未报名，
     *  0 代表 未报名 ，
     *  1 代表已经报名 但未审核 ，
     *  2 代表通过审核
     * */

    public ActivitiesEnroll(int userAccount, int activitiesId) {
        this.userAccount = userAccount;
        this.activitiesId = activitiesId;
    }
    @Ignore
    public ActivitiesEnroll(int userAccount, int activitiesId, int state) {
        this.userAccount = userAccount;
        this.activitiesId = activitiesId;
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

    public int getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(int activitiesId) {
        this.activitiesId = activitiesId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
