package com.dontbelate.Class;

/**
 * Created by Cheng on 12/5/17.
 */

public class Message {
    private String id;
    private String sender;
    private String receiver;
    private String title;
    private String content;
    private String sendTime;
    private String responseTime;
    private String status;
    private String result;
    private String type;

    public Message() {

    }

    public Message(String id, String sender, String receiver, String title, String content, String sendTime, String responseTime, String status, String result, String type) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.sendTime = sendTime;
        this.responseTime = responseTime;
        this.status = status;
        this.result = result;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

