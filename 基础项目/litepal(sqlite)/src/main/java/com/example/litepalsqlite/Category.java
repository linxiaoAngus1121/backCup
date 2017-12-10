package com.example.litepalsqlite;

import org.litepal.crud.DataSupport;

/**
 * Created by 123456 on 2017/7/18.
 */

public class Category extends DataSupport {
    private int  id;
    private  String cateroryName;
    private int categoryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCateroryName() {
        return cateroryName;
    }

    public void setCateroryName(String cateroryName) {
        this.cateroryName = cateroryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
