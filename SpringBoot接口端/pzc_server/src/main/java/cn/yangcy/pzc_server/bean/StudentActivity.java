package cn.yangcy.pzc_server.bean;

public class StudentActivity {

    private int id;
    private int organizationId;
    private String name;
    private String createOn;
    private String startTime;
    private String description;
    private int state;
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
}
