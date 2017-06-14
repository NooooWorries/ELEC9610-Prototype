package com.dontbelate.Class;

/**
 * Created by Cheng on 16/5/17.
 */

public class ServiceDetail {
    private String serviceId;
    private String consultantId;
    private String title;
    private String description;
    private String features;
    private String postDate;
    private String price;
    private String top;

    public ServiceDetail() {

    }

    public ServiceDetail(String serviceId, String consultantId, String title, String description, String features, String postDate, String price, String top) {
        this.serviceId = serviceId;
        this.consultantId = consultantId;
        this.title = title;
        this.description = description;
        this.features = features;
        this.postDate = postDate;
        this.price = price;
        this.top = top;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

}

