package com.guvi.model;

import java.util.List;

public class User {
    private String id;

    public User(String id,String email,String passwordhash,List<String> roles,boolean active) {
        this.active = active;
        this.roles = roles;
        this.passwordhash = passwordhash;
        this.email = email;
        this.id = id;
    }

    public User(String id) {
        this.id = id;
    }

    private String email;
    private String passwordhash;

    private List<String> roles;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
