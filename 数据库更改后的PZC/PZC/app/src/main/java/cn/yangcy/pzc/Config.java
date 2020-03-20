package cn.yangcy.pzc;

import okhttp3.MediaType;

public class Config {

    public static final String SP_NAME = "SP_USER";
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    public static final String SUCCESS_STATE = "ok";
    public static final String FIAL_STATE = "fail";
    public static final String ERROR_STATE = "error";

    public static final String API_URL = "http://192.168.0.102:8012/api/";

//    public static final String USER_LOGIN = "user/login";
    public static final String USER_QUERY_ACCOUNT = "user/query/account/";

    public static final String GET_ALL_ACTIVITIES_LIST = "activities/query";
    public static final String GET__ACTIVITIES_BY_ID = "activities/query/";

    public static final String GET_ALL_ORGANIZATION_LIST = "organization/query";

    public static final String GET_ORGANIZATION_BY_ID = "organization/query/";

    public static final String GET_INFORMATION_LIST = "information/query";
}
