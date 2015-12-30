package com.jhson.view.app;

public class RollingModel {

    private String key = "";
    private int resId = -1;

    public RollingModel(String key, int resId){
        this.key = key;
        this.resId = resId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
