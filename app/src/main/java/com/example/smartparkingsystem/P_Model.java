package com.example.smartparkingsystem;

public class P_Model {


    private String city_name;
    private String P_name;
    private String Longi;
    private String Lati;
    private String Address;
    private String P_space;
    private String Id;

    public P_Model() {
    }

    public P_Model(String city_name, String p_name, String p_space) {
        this.city_name = city_name;
        P_name = p_name;
        P_space = p_space;
    }

    public P_Model(String city_name, String p_name, String longi, String lati, String address, String p_space, String id, String b_fee, String c_fee) {
        this.city_name = city_name;
        P_name = p_name;
        Longi = longi;
        Lati = lati;
        Address = address;
        P_space = p_space;
        Id = id;
        B_fee = b_fee;
        C_fee = c_fee;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getLongi() {
        return Longi;
    }

    public void setLongi(String longi) {
        Longi = longi;
    }

    public String getLati() {
        return Lati;
    }

    public void setLati(String lati) {
        Lati = lati;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getP_space() {
        return P_space;
    }

    public void setP_space(String p_space) {
        P_space = p_space;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getB_fee() {
        return B_fee;
    }

    public void setB_fee(String b_fee) {
        B_fee = b_fee;
    }

    public String getC_fee() {
        return C_fee;
    }

    public void setC_fee(String c_fee) {
        C_fee = c_fee;
    }

    private String B_fee;
    private String C_fee;


}
