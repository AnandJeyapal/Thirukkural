package com.impiger.thirukkural.model;

public class Favorite {
    private int adhigaramIdx;
    private String adhigaramName;

    public int getAdhigaramIdx() {
        return adhigaramIdx;
    }

    public Favorite(int adhigaramIdx, String adhigaramName) {
        this.adhigaramIdx = adhigaramIdx;
        this.adhigaramName = adhigaramName;
    }

    public void setAdhigaramIdx(int adhigaramIdx) {
        this.adhigaramIdx = adhigaramIdx;
    }

    public String getAdhigaramName() {
        return adhigaramName;
    }

    public void setAdhigaramName(String adhigaramName) {
        this.adhigaramName = adhigaramName;
    }
    public String getLargeDesc() {
        return ""+ adhigaramName.charAt(0);
    }

}
