package com.prometheus.gallery;

import android.app.Application;
import android.view.animation.AlphaAnimation;

public class MyApplication extends Application {

    private String id;

    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
}
