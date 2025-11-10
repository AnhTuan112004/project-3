package com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity;

public class NatStudent {
    private Long Natid;
    private String Natname;
    private int Natage;
    private String Natgender;
    private String Nataddress;
    private String Natphone;
    private String Natemail;

    // Constructor không tham số
    public NatStudent() {
    }

    // Constructor đầy đủ tham số
    public NatStudent(Long Natid, String Natname, int Natage, String Natgender, String Nataddress, String Natphone, String Natemail) {
        this.Natid = Natid;
        this.Natname = Natname;
        this.Natage = Natage;
        this.Natgender = Natgender;
        this.Nataddress = Nataddress;
        this.Natphone = Natphone;
        this.Natemail = Natemail;
    }

    // Getters and Setters (Đã sửa lại cho nhất quán)
    public Long getNatid() {
        return Natid;
    }

    public void setNatid(Long Natid) {
        this.Natid = Natid;
    }

    public String getNatname() {
        return Natname;
    }

    public void setNatname(String Natname) {
        this.Natname = Natname;
    }

    public int getNatage() {
        return Natage;
    }

    public void setNatage(int Natage) {
        this.Natage = Natage;
    }

    public String getNatgender() {
        return Natgender;
    }

    public void setNatgender(String Natgender) {
        this.Natgender = Natgender;
    }

    public String getNataddress() {
        return Nataddress;
    }

    public void setNataddress(String Nataddress) {
        this.Nataddress = Nataddress;
    }

    public String getNatphone() {
        return Natphone;
    }

    public void setNatphone(String Natphone) {
        this.Natphone = Natphone;
    }

    public String getNatemail() {
        return Natemail;
    }

    public void setNatemail(String Natemail) {
        this.Natemail = Natemail;
    }
}