package com.dontbelate.Class;

/**
 * Created by Cheng on 19/5/17.
 */

public class Payment {
    private String paymentId;
    private String userId;
    private String providerId;
    private String price;
    private String timePaid;

    public Payment() {

    }

    public Payment(String paymentId, String userId, String providerId, String price, String timePaid) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.providerId = providerId;
        this.price = price;
        this.timePaid = timePaid;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(String timePaid) {
        this.timePaid = timePaid;
    }
}
