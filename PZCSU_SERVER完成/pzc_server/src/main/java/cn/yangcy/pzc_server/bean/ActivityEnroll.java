package cn.yangcy.pzc_server.bean;

public class ActivityEnroll {

    private Integer id;
    private Integer user_id;
    private Integer activity_id;
    private Integer activity_enroll_state;

    public ActivityEnroll() {
    }

    public ActivityEnroll(Integer user_id, Integer activity_id) {
        this.user_id = user_id;
        this.activity_id = activity_id;
    }

    public ActivityEnroll(Integer user_id, Integer activity_id, Integer activity_enroll_state) {
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.activity_enroll_state = activity_enroll_state;
    }

    public ActivityEnroll(Integer id, Integer user_id, Integer activity_id, Integer activity_enroll_state) {
        this.id = id;
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.activity_enroll_state = activity_enroll_state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    public Integer getActivity_enroll_state() {
        return activity_enroll_state;
    }

    public void setActivity_enroll_state(Integer activity_enroll_state) {
        this.activity_enroll_state = activity_enroll_state;
    }

    @Override
    public String toString() {
        return "ActivityEnroll{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", activity_id=" + activity_id +
                ", activity_enroll_state=" + activity_enroll_state +
                '}';
    }
}
