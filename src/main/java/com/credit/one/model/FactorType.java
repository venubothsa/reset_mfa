package com.credit.one.model;

public class FactorType {
    public FactorType() {

    }

    public FactorType(String id, String label) {
        this.id = id;
        this.label = label;
    }

    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
