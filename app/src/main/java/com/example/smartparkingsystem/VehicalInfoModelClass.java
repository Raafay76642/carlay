package com.example.smartparkingsystem;

public class VehicalInfoModelClass {
    String type,regNo;

    public VehicalInfoModelClass(String type, String regNo) {
        this.type = type;
        this.regNo = regNo;
    }

    public VehicalInfoModelClass() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
