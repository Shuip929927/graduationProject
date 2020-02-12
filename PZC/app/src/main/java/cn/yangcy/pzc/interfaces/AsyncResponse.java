package cn.yangcy.pzc.interfaces;

public interface AsyncResponse {

    void onDataReceivedSuccess(Object object);
    void onDataReceivedFailed();
}
