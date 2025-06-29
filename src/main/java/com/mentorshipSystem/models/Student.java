package com.mentorshipSystem.models;

public class Student {
    private int id;
    private String name;
    private int term;
    private String phone_number;

    public Student(int id, String name, int term, String phone_number) {
        this.id = id;
        this.name = name;
        this.term = term;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number){
        this.phone_number = phone_number;
    }
}
