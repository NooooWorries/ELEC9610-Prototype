package com.dontbelate.Class;

/**
 * Created by Cheng on 7/5/17.
 */

public class ShowCard {
    private String name;
    private String gender;
    private int thumbnail;
    private String userId;

    public ShowCard() {

    }

    public ShowCard(String name, String gender, int thumbnail) {
        this.name = name;
        this.gender = gender;
        this.thumbnail = thumbnail;
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
