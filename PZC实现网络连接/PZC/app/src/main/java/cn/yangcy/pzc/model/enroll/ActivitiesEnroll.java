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
    private int user_id;

    @ColumnInfo(name = "activity_id")
//    @ForeignKey(entity = Activities.class,parentColumns = "id",childColumns = "activity_id")
    private int activity_id;

    @ColumnInfo(name = "activity_enroll_state",defaultValue = "0")
    private int activity_enroll_state;
    /*
     *  不存在 代表未报名，
     *  0 代表 未报名 ，
     *  1 代表已经报名 但未审核 ，
     *  2 代表通过审核
     * */

    @Ignore
    public ActivitiesEnroll() {
    }

    public ActivitiesEnroll(int user_id, int activity_id) {
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.activity_enroll_state = 0;
    }
    @Ignore
    public ActivitiesEnroll(int user_id, int activity_id, int activity_enroll_state) {
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.activity_enroll_state = activity_enroll_state;
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

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivity_enroll_state() {
        return activity_enroll_state;
    }

    public void setActivity_enroll_state(int activity_enroll_state) {
        this.activity_enroll_state = activity_enroll_state;
    }

    @Override
    public String toString() {
        return "ActivitiesEnroll{" +
                "id=" + id +
                ", userAccount=" + user_id +
                ", activitiesId=" + activity_id +
                ", state=" + activity_enroll_state +
                '}';
    }
}
