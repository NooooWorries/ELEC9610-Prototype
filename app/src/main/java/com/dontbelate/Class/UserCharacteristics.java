package com.dontbelate.Class;

/**
 * Created by Cheng on 16/5/17.
 */

public class UserCharacteristics {
    // Attributes
    private String height;
    private String weight;
    private String martialStatus;
    private String educationBackground;
    private String placeOfBirth;
    private String occupation;
    private String belief;
    private String child;
    private String soliloquy;
    private String happyThings;
    private String impression;
    private String stuffCannotDoWithout;
    private String idealPartnerCharacteristics;

    // Constructors
    public UserCharacteristics() {
        // Accept null
    }

    public UserCharacteristics(String height, String weight, String martialStatus,
                               String educationBackground, String placeOfBirth, String occupation,
                               String belief, String child, String soliloquy, String happyThings,
                               String impression, String stuffCannotDoWithout, String idealPartnerCharacteristics) {
        this.height = height;
        this.weight = weight;
        this.martialStatus = martialStatus;
        this.educationBackground = educationBackground;
        this.placeOfBirth = placeOfBirth;
        this.occupation = occupation;
        this.belief = belief;
        this.child = child;
        this.soliloquy = soliloquy;
        this.happyThings = happyThings;
        this.impression = impression;
        this.stuffCannotDoWithout = stuffCannotDoWithout;
        this.idealPartnerCharacteristics = idealPartnerCharacteristics;
    }

    // Getters
    public String getHeight() {
        return this.height;
    }

    // Setter
    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBelief() {
        return belief;
    }

    public void setBelief(String belief) {
        this.belief = belief;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getHappyThings() {
        return happyThings;
    }

    public void setHappyThings(String happyThings) {
        this.happyThings = happyThings;
    }

    public String getIdealPartnerCharacteristics() {
        return idealPartnerCharacteristics;
    }

    public void setIdealPartnerCharacteristics(String idealPartnerCharacteristics) {
        this.idealPartnerCharacteristics = idealPartnerCharacteristics;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getSoliloquy() {
        return soliloquy;
    }

    public void setSoliloquy(String soliloquy) {
        this.soliloquy = soliloquy;
    }

    public String getStuffCannotDoWithout() {
        return stuffCannotDoWithout;
    }

    public void setStuffCannotDoWithout(String stuffCannotDoWithout) {
        this.stuffCannotDoWithout = stuffCannotDoWithout;
    }

}
