package com.example.web.app.dao.model;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Date birthday;
    private String numberPhone;
    private String activity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimeBirthday() { return birthday.getTime(); }

    public Date getBirthday() { return birthday; }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getActivity() { return activity; }

    public void setActivity(String activity) { this.activity = activity; }
}