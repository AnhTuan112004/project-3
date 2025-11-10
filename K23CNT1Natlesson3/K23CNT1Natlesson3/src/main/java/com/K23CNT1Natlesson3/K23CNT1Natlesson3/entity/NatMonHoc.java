package com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity;

public class NatMonHoc {
    private String NatMamh;
    private String NatTenmh;
    private int NatSotiet;

    // Constructor
    public NatMonHoc() {
    }

    public NatMonHoc(String NatMamh, String NatTenmh, int NatSotiet) {
        this.NatMamh = NatMamh;
        this.NatTenmh = NatTenmh;
        this.NatSotiet = NatSotiet;
    }

    // Getters and Setters
    public String getNatMamh() {
        return NatMamh;
    }

    public void setNatMamh(String NatMamh) {
        this.NatMamh = NatMamh;
    }

    public String getNatTenmh() {
        return NatTenmh;
    }

    public void setNatTenmh(String NatTenmh) {
        this.NatTenmh = NatTenmh;
    }

    public int getNatSotiet() {
        return NatSotiet;
    }

    public void setNatSotiet(int NatSotiet) {
        this.NatSotiet = NatSotiet;
    }
}