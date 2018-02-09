package com.my.forward.mvp.helpna.bean;

/**
 * Created by 123456 on 2018/1/31.
 */

public class Information {
    private String odd;
    private String name;
    private String contact;
    private String query_address;
    private String address;

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQuery_address() {
        return query_address;
    }

    public void setQuery_address(String query_address) {
        this.query_address = query_address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Information(String odd, String name, String contact, String query_address, String
            address) {
        this.odd = odd;
        this.name = name;
        this.contact = contact;
        this.query_address = query_address;
        this.address = address;
    }
}
