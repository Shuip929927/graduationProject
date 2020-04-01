package cn.yangcy.pzc;

import okhttp3.MediaType;

public class Config {

    public static final String SP_NAME = "SP_USER";
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static final String SUCCESS_STATE = "ok";
    public static final String FAIL_STATE = "fail";
    public static final String ERROR_STATE = "error";

    public static final String API_URL = "http://192.168.0.104:8012/api/";

//    public static final String USER_LOGIN = "user/login";

    public static final String GET_USER_BY_ACCOUNT = "user/query/account/";
    public static final String GET_ORG_STATE2_USER_LIST_BY_ORG_ID = "user/queryOrgState2/organizationId/";
    public static final String GET_ORG_STATE1_USER_LIST_BY_ORG_ID = "user/queryOrgState1/organizationId/";
    public static final String GET_ACT_STATE2_USER_LIST_BY_ACT_ID = "user/queryActState2/activityId/";
    public static final String GET_ACT_STATE1_USER_LIST_BY_ACT_ID = "user/queryActState1/activityId/";
    public static final String POST_USER_REGISTER = "user/add";
    public static final String PUT_UPDATE_USER = "user/update";


    public static final String GET_ALL_ACTIVITIES = "activity/queryAll";
    public static final String GET_ACTIVITIES_BY_ID = "activity/query/id/";
    public static final String GET_ORG_HOLD_ACT_BY_USER_ID = "activity/queryOrgHold/userId/";
    public static final String GET_ENROLL_STATE2_ACTIVITIES_LIST_BY_USER_ID = "activity/queryActState2/userId/";
    public static final String POST_ADD_ACTIVITIES = "activity/add";
    public static final String DELETE_ACTIVITIES = "activity/delete/";
    public static final String PUT_UPDATE_ACTIVITIES = "activity/update";

    public static final String GET_AE_BY_USER_ID_AND_ACT_ID = "ae/query/userId&activityId/";
    public static final String POST_ADD_AE = "ae/add";
    public static final String PUT_UPDATE_AE = "ae/update";

    public static final String GET_ALL_ORGANIZATION_LIST = "organization/queryAll";
    public static final String GET_ORGANIZATION_BY_ID = "organization/query/id/";
    public static final String GET_USER_ENROLL_STATE2_ORG_LIST_BY_USER_ID = "organization/queryUserEnrollState2/userId/";
    public static final String POST_ADD_ORGANIZATION = "organization/add";
    public static final String DELETE_ORGANIZATION = "organization/delete/";
    public static final String PUT_UPDATE_ORGANIZATION = "organization/update";

    public static final String GET_OE_BY_USER_ID_AND_ORG_ID = "oe/query/userId&organizationId/";
    public static final String POST_ADD_OE = "oe/add";
    public static final String DELETE_ADD_OE_BY_ID = "oe/delete/";
    public static final String PUT_UPDATE_OE = "oe/update";

    public static final String GET_DEPARTMENT_BY_ID = "department/query/id/";
    public static final String GET_DEPARTMENT_ALL = "department/queryAll";

    public static final String GET_MAJOR_BY_ID = "major/query/id/";
    public static final String GET_MAJOR_BY_DEP_ID = "major/query/departmentId/";
    public static final String GET_MAJOR_ALL = "major/queryAll";


    public static final String GEI_ALL_INFORMATION = "information/queryAll";
    public static final String PUT_UPDATE_INFORMATION = "information/update";


}
