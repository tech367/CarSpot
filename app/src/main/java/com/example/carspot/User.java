package com.example.carspot;

public class User {
    String user_name;
    String phone;
    String password;
    String e_mail;

    public User(String user_name, String phone, String password, String e_mail) {
        this.user_name = user_name;
        this.phone = phone;
        this.password = password;
        this.e_mail = e_mail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
}
