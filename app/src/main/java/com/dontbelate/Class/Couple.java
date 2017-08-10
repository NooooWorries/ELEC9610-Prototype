package com.dontbelate.Class;

public class Couple {
    private String id;
    private String partyOneId;
    private String partyTwoId;
    private String start;
    private String status;

    public Couple() {

    }

    public Couple(String id, String partyOneId, String partyTwoId, String start, String status) {
        this.id = id;
        this.partyOneId = partyOneId;
        this.partyTwoId = partyTwoId;
        this.start = start;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyOneId() {
        return partyOneId;
    }

    public void setPartyOneId(String partyOneId) {
        this.partyOneId = partyOneId;
    }

    public String getPartyTwoId() {
        return partyTwoId;
    }

    public void setPartyTwoId(String partyTwoId) {
        this.partyTwoId = partyTwoId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}