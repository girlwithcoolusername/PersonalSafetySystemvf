package com.example.personalsafetysystem.Model;

import java.util.Map;

public class User {

    private String img_url;
    private int heartbeats;
    private Map<String, Object> contacts_list;
    private String name;
    private int nbrOfSteps;
    private String phone;
    private String role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    // Empty constructor required by Firebase
    public User() {
    }

    public User( String img_url, int heartbeats, Map<String, Object> list_contacts, String name, int nbrOfSteps, String phone, String role, String password) {
        this.img_url = img_url;
        this.heartbeats = heartbeats;
        this.contacts_list = list_contacts;
        this.name = name;
        this.nbrOfSteps = nbrOfSteps;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }
// Constructeur


    // Accesseurs et mutateurs

    public int getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(int heartbeats) {
        this.heartbeats = heartbeats;
    }


    public Map<String, Object> getContacts_list() {
        return contacts_list;
    }

    public void setContacts_list(Map<String, Object> contacts_list) {
        this.contacts_list = contacts_list;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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


