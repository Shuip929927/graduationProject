package cn.yangcy.pzc_server.bean;

public class Activity {

    private int id;
    private int organization_id;
    private String activity_name;
    private String create_time;
    private String start_time;
    private String description;
    private int activity_state;
    /*
     * 0 代表活动 进行中
     * 1 代表活动结束
     * 2 代表活动长期有效
     * */

    private int isDelete;
    /*
     * 0 代表活动存在
     * 1 代表活动删除
     * */

    public Activity() {
    }

    public Activity(int organization_id, String activity_name, String create_time,
                    String start_time, String description, int activity_state, int isDelete) {
        this.organization_id = organization_id;
        this.activity_name = activity_name;
        this.create_time = create_time;
        this.start_time = start_time;
        this.description = description;
        this.activity_state = activity_state;
        this.isDelete = isDelete;
    }

    public Activity(int organization_id, String activity_name, String create_time,
                    String start_time, String description, int activity_state) {
        this.organization_id = organization_id;
        this.activity_name = activity_name;
        this.create_time = create_time;
        this.start_time = start_time;
        this.description = description;
        this.activity_state = activity_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActivity_state() {
        return activity_state;
    }

    public void setActivity_state(int activity_state) {
        this.activity_state = activity_state;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Activity[" +
                "id=" + id +
                ", organization_id=" + organization_id +
                ", activity_name='" + activity_name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", start_time='" + start_time + '\'' +
                ", description='" + description + '\'' +
                ", activity_state=" + activity_state +
                ", isDelete=" + isDelete +
                ']';
    }
}
