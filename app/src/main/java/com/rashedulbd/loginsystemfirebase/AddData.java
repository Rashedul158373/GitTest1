package com.rashedulbd.loginsystemfirebase;

public class AddData {

    private String full_Name;
    private String father_Name;
    private String mother_Name;
    private String nid_Number;
    private String address_loc;

    public AddData() {
        //empty constructor
    }

    public AddData(String full_Name, String father_Name, String mother_Name, String nid_Number, String address_loc) {
        this.full_Name = full_Name;
        this.father_Name = father_Name;
        this.mother_Name = mother_Name;
        this.nid_Number = nid_Number;
        this.address_loc = address_loc;
    }

    public String getFull_Name() {
        return full_Name;
    }

    public void setFull_Name(String full_Name) {
        this.full_Name = full_Name;
    }

    public String getFather_Name() {
        return father_Name;
    }

    public void setFather_Name(String father_Name) {
        this.father_Name = father_Name;
    }

    public String getMother_Name() {
        return mother_Name;
    }

    public void setMother_Name(String mother_Name) {
        this.mother_Name = mother_Name;
    }

    public String getNid_Number() {
        return nid_Number;
    }

    public void setNid_Number(String nid_Number) {
        this.nid_Number = nid_Number;
    }

    public String getAddress_loc() {
        return address_loc;
    }

    public void setAddress_loc(String address_loc) {
        this.address_loc = address_loc;
    }
}
