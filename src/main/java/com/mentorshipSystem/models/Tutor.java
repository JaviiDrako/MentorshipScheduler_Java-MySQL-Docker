package com.mentorshipSystem.models;

public class Tutor {
    private int id;
    private String name;
    private String area;
    private String phone_number;

    public Tutor(int id, String name, String area, String phone_number) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.phone_number = phone_number;
    }

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number){
        this.phone_number = phone_number;
    }
}
