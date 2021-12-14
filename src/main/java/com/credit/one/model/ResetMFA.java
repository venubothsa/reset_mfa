package com.credit.one.model;

public class ResetMFA {
    private String userName;
    private String ssn;
    private String globalPin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getGlobalPin() {
        return globalPin;
    }

    public void setGlobalPin(String globalPin) {
        this.globalPin = globalPin;
    }
}
