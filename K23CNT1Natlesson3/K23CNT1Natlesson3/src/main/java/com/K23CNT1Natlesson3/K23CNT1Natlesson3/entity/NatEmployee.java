package com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity;

public class NatEmployee {
    private Long NatId;
    private String NatFullName;
    private String NatGender;
    private int NatAge;
    private double NatSalary;

    // Constructor
    public NatEmployee() {
    }

    public NatEmployee(Long NatId, String NatFullName, String NatGender, int NatAge, double NatSalary) {
        this.NatId = NatId;
        this.NatFullName = NatFullName;
        this.NatGender = NatGender;
        this.NatAge = NatAge;
        this.NatSalary = NatSalary;
    }

    // Getters and Setters
    public Long getNatId() {
        return NatId;
    }

    public void setNatId(Long NatId) {
        this.NatId = NatId;
    }

    public String getNatFullName() {
        return NatFullName;
    }

    public void setNatFullName(String NatFullName) {
        this.NatFullName = NatFullName;
    }

    public String getNatGender() {
        return NatGender;
    }

    public void setNatGender(String NatGender) {
        this.NatGender = NatGender;
    }

    public int getNatAge() {
        return NatAge;
    }

    public void setNatAge(int NatAge) {
        this.NatAge = NatAge;
    }

    public double getNatSalary() {
        return NatSalary;
    }

    public void setNatSalary(double NatSalary) {
        this.NatSalary = NatSalary;
    }
}