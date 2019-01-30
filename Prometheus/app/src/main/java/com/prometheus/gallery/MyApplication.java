package com.prometheus.gallery;

import android.app.Application;
import android.view.animation.AlphaAnimation;

import com.prometheus.gallery.obj.User;

public class MyApplication extends Application {

//    private String id = "";
//
//    private String userType = "";
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private User userobj = null;

    public User getUserobj() {
        return userobj;
    }

    public void setUserobj(User userobj) {
        this.userobj = userobj;
    }
}
