package com.example.materialdesign;

/**
 * Created by 123456 on 2017/7/23.
 */

public class Fruit {

    String name;
    int imageid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public Fruit(String name, int imageid) {

        this.name = name;
        this.imageid = imageid;
    }
}
