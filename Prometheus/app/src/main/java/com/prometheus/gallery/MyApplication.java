package com.prometheus.gallery;

import android.app.Application;

public class MyApplication extends Application {

    private boolean loginstate;
    private String id;

    public boolean getLoginstate() {
        return loginstate;
    }

    public void setLoginstate(boolean loginstate) {
        this.loginstate = loginstate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
