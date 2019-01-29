package com.prometheus.gallery.obj;

import java.io.Serializable;

public class LoveObj implements Serializable {

    private String id = "";
    private String postid = "";
    private String userid = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "LoveObj{" +
                "id='" + id + '\'' +
                ", postid='" + postid + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
