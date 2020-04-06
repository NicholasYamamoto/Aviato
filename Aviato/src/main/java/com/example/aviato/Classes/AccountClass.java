package com.example.aviato.Classes;

public class AccountClass {
    private int accountID;
    private String email;
    private String password;

    public AccountClass() {
    }

    public AccountClass(String email) {
        this.email = email;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getDefaultCarrier() {
//        return defaultCarrier;
//    }
//
//    public void setDefaultCarrier(String defaultCarrier) {
//        this.defaultCarrier = defaultCarrier;
//    }
//
//    public String getDefaultDepart() {
//        return defaultDepart;
//    }
//
//    public void setDefaultDepart(String defaultDepart) {
//        this.defaultDepart = defaultDepart;
//    }
}


