package com.dontbelate.Class;

/**
 * Created by Cheng on 30/4/17.
 */

public class UserDetail {
    // attributes
    public String name;
    public String gender;
    public String userType;
    public String street;
    public String suburb;
    public String city;
    public String state;
    public String postcode;
    public String university;
    public String phone;
    public String birthday;
    public String isPublished;
    public String userId;
    public String coupleId;
    public String featured;


    // Constructors
    public UserDetail() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserDetail(String name, String gender, String userType, String street, String suburb, String city, String state, String postcode, String university, String phone, String birthday, String isPublished, String userId, String coupleId, String featured) {
        this.name = name;
        this.gender = gender;
        this.userType = userType;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.university = university;
        this.phone = phone;
        this.birthday = birthday;
        this.isPublished = isPublished;
        this.userId = userId;
        this.coupleId = coupleId;
        this.featured = featured;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(String coupleId) {
        this.coupleId = coupleId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
