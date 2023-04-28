package com.example.personalsafetysystem.Model;

import java.util.Map;

public class User {
    private String uid;
    private int heartbeats;
    private Map<String, String> list_contacts;
    private String name;
    private int nbrOfSteps;
    private String phone;
    private String role;

    // Constructeur
    public User(String name, String role, String phone, Map<String, String> list_contacts, int heartbeats, int nbrOfSteps) {
        this.heartbeats = heartbeats;
        this.list_contacts = list_contacts;
        this.name = name;
        this.nbrOfSteps = nbrOfSteps;
        this.phone = phone;
        this.role = role;
    }

    // Accesseurs et mutateurs
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(int heartbeats) {
        this.heartbeats = heartbeats;
    }

    public Map<String, String> getList_contacts() {
        return list_contacts;
    }

    public void setList_contacts(Map<String, String> list_contacts) {
        this.list_contacts = list_contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbrOfSteps() {
        return nbrOfSteps;
    }

    public void setNbrOfSteps(int nbrOfSteps) {
        this.nbrOfSteps = nbrOfSteps;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


