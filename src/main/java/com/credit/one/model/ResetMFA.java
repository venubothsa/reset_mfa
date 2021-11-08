package com.credit.one.model;

public class ResetMFA {
    private String firstName;
    private String lastName;
    private String globalPin;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGlobalPin() {
        return globalPin;
    }

    public void setGlobalPin(String globalPin) {
        this.globalPin = globalPin;
    }
}
