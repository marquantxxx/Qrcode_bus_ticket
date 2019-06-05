package com.marquant.inc.busticket;

public class User {

    private int id;
    private String fullname;
    private String email;
    private String phone;
    private String secret_text;
    private String password;
    private String gender;

    public User(String fullname, String email, String phone, String secret_text, String password, String gender) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.secret_text = secret_text;
        this.password = password;
        this.gender = gender;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecret_text() {
        return secret_text;
    }

    public void setSecret_text(String secret_text) {
        this.secret_text = secret_text;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
