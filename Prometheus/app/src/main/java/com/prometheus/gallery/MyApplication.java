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

    private String gobacklogin = "";

    private String comefromhomedetail="";

    public String getComefromhomedetail() {
        return comefromhomedetail;
    }

    public void setComefromhomedetail(String comefromhomedetail) {
        this.comefromhomedetail = comefromhomedetail;
    }

    public String getPostidforgoback() {
        return postidforgoback;
    }

    public void setPostidforgoback(String postidforgoback) {
        this.postidforgoback = postidforgoback;
    }

    private String postidforgoback = "";

    public String getGobacklogin() {
        return gobacklogin;
    }

    public void setGobacklogin(String gobacklogin) {
        this.gobacklogin = gobacklogin;
    }

    public User getUserobj() {
        return userobj;
    }

    public void setUserobj(User userobj) {
        this.userobj = userobj;
    }


}
