package com.dontbelate.Class;

/**
 * Created by Cheng on 16/5/17.
 */

public class Consult {
    private String consultId;
    private String coupleId;
    private String consultantId;
    private String startTime;
    private String price;
    private String status;

    public Consult() {

    }

    public Consult(String consultId, String coupleId, String consultantId, String startTime, String price, String status) {
        this.consultId = consultId;
        this.coupleId = coupleId;
        this.consultantId = consultantId;
        this.startTime = startTime;
        this.price = price;
        this.status = status;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultId() {
        return consultId;
    }

    public void setConsultId(String consultId) {
        this.consultId = consultId;
    }

    public String getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(String coupleId) {
        this.coupleId = coupleId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Created by Cheng on 14/5/17.
     */


    /**
     * Created by Cheng on 15/5/17.
     */


    /**
     * Created by Cheng on 6/5/17.
     */


}
