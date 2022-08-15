package com.example.SpringBoot;

public class ResponseObject {
    private String alert;
    private Object data;

    public ResponseObject(String alert, Object data) {
        this.alert = alert;
        this.data = data;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
