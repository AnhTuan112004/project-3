package com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity;

public class NatKhoa {
    private String NatMakh;
    private String NatTenkh;

    // Constructor
    public NatKhoa() {
    }

    public NatKhoa(String NatMakh, String NatTenkh) {
        this.NatMakh = NatMakh;
        this.NatTenkh = NatTenkh;
    }

    // Getters and Setters
    public String getNatMakh() {
        return NatMakh;
    }

    public void setNatMakh(String NatMakh) {
        this.NatMakh = NatMakh;
    }

    public String getNatTenkh() {
        return NatTenkh;
    }

    public void setNatTenkh(String NatTenkh) {
        this.NatTenkh = NatTenkh;
    }
}