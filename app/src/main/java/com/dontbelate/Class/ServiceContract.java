package com.dontbelate.Class;

/**
 * Created by Cheng on 19/5/17.
 */

public class ServiceContract {
    private String userId;
    private String providerId;

    public ServiceContract() {

    }

    public ServiceContract(String userId, String providerId) {
        this.userId = userId;
        this.providerId = providerId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
