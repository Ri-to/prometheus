package com.prometheus.gallery.obj;

import java.io.Serializable;

public class PostObj implements Serializable {
    private String id = "";
    private String category = "";
    private String Title = "";
    private Double price = 0.00;
    private String photoPath = "";
    private String createdBy = "";
    private String createdDate = "";
    private String modifiedDate = "";
    private String description = "";
    private String framesize = "";
    private int viewCount = 0;
    private int loveCount = 0;

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public String getId() {
        return id;
    }

    public String getFramesize() {
        return framesize;
    }

    public void setFramesize(String framesize) {
        this.framesize = framesize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "PostObj{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", Title='" + Title + '\'' +
                ", price=" + price +
                ", photoPath='" + photoPath + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", description='" + description + '\'' +
                ", framesize='" + framesize + '\'' +
                ", viewCount=" + viewCount +
                ", loveCount=" + loveCount +
                '}';
    }
}
