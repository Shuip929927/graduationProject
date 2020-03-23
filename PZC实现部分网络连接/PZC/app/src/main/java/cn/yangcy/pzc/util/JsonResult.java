package cn.yangcy.pzc.util;

public class JsonResult {

    private String status = null;

    private Object result = null;

    private Object data = null;

    public JsonResult status(String status) {
        this.status = status;
        return this;
    }

    public JsonResult(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

