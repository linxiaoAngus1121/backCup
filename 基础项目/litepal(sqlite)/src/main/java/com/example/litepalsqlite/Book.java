package com.example.litepalsqlite;

import org.litepal.crud.DataSupport;

/**
 * Created by 123456 on 2017/7/16.
 */

public class Book extends DataSupport {
    private  int id;
    private  String name;
    private String author;
    private double prices;
    private int pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
